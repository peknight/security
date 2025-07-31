package com.peknight.security.bouncycastle

import cats.Monad
import cats.data.{EitherT, NonEmptyList}
import cats.effect.{Resource, Sync}
import cats.syntax.applicative.*
import cats.syntax.either.*
import cats.syntax.functor.*
import cats.syntax.option.*
import com.peknight.cats.ext.syntax.eitherT.{eLiftET, rLiftET}
import com.peknight.error.Error
import com.peknight.error.option.OptionEmpty
import com.peknight.error.std.WrongClassTag
import com.peknight.error.syntax.applicativeError.asError
import com.peknight.fs2.io.ext.syntax.path.createParentDirectories
import com.peknight.method.cascade.{Source, fetch as cascadeFetch}
import com.peknight.security.bouncycastle.openssl.jcajce.{JcaPEMKeyConverter, JcaPEMWriter}
import com.peknight.security.bouncycastle.pkix.syntax.jcaPEMKeyConverter.getKeyPairF
import com.peknight.security.bouncycastle.pkix.syntax.jcaPEMWriter.writeObjectF
import com.peknight.security.bouncycastle.pkix.syntax.pemParser.readObjectF
import com.peknight.security.pkix.pipe.{certificate, pemObject, x509Certificate}
import com.peknight.security.provider.Provider
import fs2.Stream
import fs2.io.file.{Files, Path}
import fs2.text.utf8
import org.bouncycastle.openssl.jcajce.JcaPEMWriter as JJcaPEMWriter
import org.bouncycastle.openssl.{PEMKeyPair, PEMParser as JPEMParser}

import java.security.cert.X509Certificate
import java.security.{KeyPair, Provider as JProvider}

package object openssl:

  def readPEM[F[_]: {Sync, Files}, A](path: Path)(f: NonEmptyList[AnyRef] => F[Either[Error, A]])
  : F[Either[Error, Option[A]]] =
    val read =
      for
        values <- EitherT(Resource.fromAutoCloseable[F, JPEMParser](PEMParser[F](path)).use { parser =>
          Monad[F].tailRecM[List[AnyRef], Option[NonEmptyList[AnyRef]]](Nil)(acc => parser.readObjectF[F].map {
            case Some(obj) => (obj :: acc).asLeft
            case _ => acc.reverse match
              case head :: tail => NonEmptyList(head, tail).some.asRight
              case _ => none[NonEmptyList[AnyRef]].asRight
          })
        }.asError)
        value <- values match
          case Some(values) => EitherT(f(values)).map(_.some)
          case _ => none[A].rLiftET[F, Error]
      yield
        value
    Monad[[X] =>> EitherT[F, Error, X]].ifM[Option[A]](EitherT(Files[F].exists(path).asError))(
      read,
      none[A].rLiftET[F, Error]
    ).value

  def writePEM[F[_] : {Sync, Files}](path: Path)(f: JJcaPEMWriter => F[Unit]): F[Either[Error, Unit]] =
    val eitherT =
      for
        _ <- EitherT(path.createParentDirectories[F].asError)
        _ <- EitherT(Resource.fromAutoCloseable[F, JJcaPEMWriter](JcaPEMWriter[F](path)).use(f).asError)
      yield
        ()
    eitherT.value

  def fetch[F[_]: {Sync, Files}, A](label: String,
                                    read: F[Either[Error, Option[A]]],
                                    write: A => F[Either[Error, Unit]],
                                    source: F[Either[Error, A]]): F[Either[Error, A]] =
    val eitherT =
      for
        value <- EitherT(cascadeFetch(Source(read, write), Source.read(source.map(_.map(_.some)))))
        value <- value.toRight(OptionEmpty.label(label)).eLiftET[F]
      yield
        value
    eitherT.value

  def readPEMKeyPair[F[_]: {Sync, Files}](path: Path, provider: Option[Provider | JProvider] = None)
  : F[Either[Error, Option[KeyPair]]] =
    readPEM[F, KeyPair](path) { pemKeyPairs =>
      pemKeyPairs.collect { case pemKeyPair: PEMKeyPair => pemKeyPair }
        .headOption
        .map(pemKeyPair => JcaPEMKeyConverter(provider = provider).getKeyPairF[F](pemKeyPair).asError)
        .getOrElse(WrongClassTag[PEMKeyPair](pemKeyPairs).asLeft[KeyPair].pure[F])
    }

  def writePEMKeyPair[F[_] : {Sync, Files}](path: Path)(keyPair: KeyPair): F[Either[Error, Unit]] =
    writePEM[F](path)(_.writeObjectF[F](keyPair))

  def fetchKeyPair[F[_]: {Sync, Files}](path: Path, provider: Option[Provider | JProvider] = None)
                                       (source: F[Either[Error, KeyPair]]): F[Either[Error, KeyPair]] =
    fetch[F, KeyPair]("keyPair", readPEMKeyPair[F](path, provider), writePEMKeyPair[F](path), source)

  def readX509Certificates[F[_]: {Sync, Files}](path: Path, provider: Option[Provider | JProvider] = None)
  : F[Either[Error, Option[NonEmptyList[X509Certificate]]]] =
    Monad[[X] =>> EitherT[F, Error, X]].ifM[Option[NonEmptyList[X509Certificate]]](EitherT(Files[F].exists(path).asError))(
      EitherT(Files[F].readAll(path).through(utf8.decode)
        .through(pemObject.decode[F]).through(x509Certificate.decode[F](provider))
        .compile.toList.map {
          case head :: tail => NonEmptyList(head, tail).some
          case _ => none[NonEmptyList[X509Certificate]]
        }.asError
      ),
      none[NonEmptyList[X509Certificate]].rLiftET[F, Error]
    ).value

  def writeX509Certificates[F[_]: {Sync, Files}](path: Path)(certificates: NonEmptyList[X509Certificate])
  : F[Either[Error, Unit]] =
    val eitherT =
      for
        _ <- EitherT(path.createParentDirectories[F].asError)
        _ <- EitherT(Stream.emits(certificates.toList).covary[F]
          .through(certificate.encode[F])
          .through(pemObject.encode[F])
          .intersperse("\n")
          .through(utf8.encode[F])
          .through(Files[F].writeAll(path))
          .compile.drain.asError)
      yield
        ()
    eitherT.value

  def fetchX509Certificates[F[_]: {Sync, Files}](path: Path, provider: Option[Provider | JProvider] = None)
                                                (source: F[Either[Error, NonEmptyList[X509Certificate]]])
  : F[Either[Error, NonEmptyList[X509Certificate]]] =
    fetch[F, NonEmptyList[X509Certificate]]("x509Certificates", readX509Certificates[F](path, provider),
      writeX509Certificates[F](path), source)

  def readX509CertificatesAndKeyPair[F[_]: {Sync, Files}](certPath: Path, keyPath: Path,
                                                          certProvider: Option[Provider | JProvider] = None,
                                                          keyProvider: Option[Provider | JProvider] = None)
  : F[Either[Error, Option[(NonEmptyList[X509Certificate], KeyPair)]]] =
    val eitherT =
      for
        certOption <- EitherT(readX509Certificates[F](certPath, certProvider))
        keyPairOption <- EitherT(readPEMKeyPair[F](keyPath, keyProvider))
      yield
        for
          cert <- certOption
          keyPair <- keyPairOption
        yield
          (cert, keyPair)
    eitherT.value

  def writeX509CertificatesAndKeyPair[F[_]: {Sync, Files}](certPath: Path, keyPath: Path)
                                                          (certificates: NonEmptyList[X509Certificate], keyPair: KeyPair)
  : F[Either[Error, Unit]] =
    val eitherT =
      for
        _ <- EitherT(writeX509Certificates[F](certPath)(certificates))
        _ <- EitherT(writePEMKeyPair[F](keyPath)(keyPair))
      yield
        ()
    eitherT.value

  def fetchX509CertificatesAndKeyPair[F[_]: {Sync, Files}](certPath: Path, keyPath: Path,
                                                           certProvider: Option[Provider | JProvider] = None,
                                                           keyProvider: Option[Provider | JProvider] = None
                                                          )
                                                          (source: F[Either[Error, (NonEmptyList[X509Certificate], KeyPair)]])
  : F[Either[Error, (NonEmptyList[X509Certificate], KeyPair)]] =
    fetch[F, (NonEmptyList[X509Certificate], KeyPair)](
      "x509CertificatesAndKeyPair",
      readX509CertificatesAndKeyPair[F](certPath, keyPath, certProvider, keyProvider),
      writeX509CertificatesAndKeyPair[F](certPath, keyPath),
      source
    )

end openssl
