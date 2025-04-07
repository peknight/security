package com.peknight.security.bouncycastle

import cats.Monad
import cats.data.{EitherT, NonEmptyList}
import cats.effect.{Resource, Sync}
import cats.syntax.applicative.*
import cats.syntax.either.*
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import cats.syntax.option.*
import com.peknight.cats.ext.syntax.eitherT.{eLiftET, lLiftET, rLiftET}
import com.peknight.error.Error
import com.peknight.error.option.OptionEmpty
import com.peknight.error.std.WrongClassTag
import com.peknight.error.syntax.applicativeError.asError
import com.peknight.method.cascade.{Source, fetch}
import com.peknight.security.bouncycastle.cert.jcajce.JcaX509CertificateConverter
import com.peknight.security.bouncycastle.openssl.jcajce.{JcaPEMKeyConverter, JcaPEMWriter}
import com.peknight.security.bouncycastle.pkix.syntax.jcaPEMKeyConverter.getKeyPairF
import com.peknight.security.bouncycastle.pkix.syntax.jcaPEMWriter.{flushF, writeObjectF}
import com.peknight.security.bouncycastle.pkix.syntax.jcaX509CertificateConverter.getCertificateF
import com.peknight.security.bouncycastle.pkix.syntax.pemParser.readObjectF
import com.peknight.security.provider.Provider
import com.peknight.validation.std.either.typed
import fs2.io.file.{Files, Path}
import org.bouncycastle.cert.X509CertificateHolder
import org.bouncycastle.openssl.jcajce.JcaPEMWriter as JJcaPEMWriter
import org.bouncycastle.openssl.{PEMKeyPair, PEMParser as JPEMParser}

import java.security.cert.X509Certificate
import java.security.{KeyPair, Provider as JProvider}

package object openssl:

  private def readPem[F[_]: {Sync, Files}, A, B](path: Path)(f: JPEMParser => F[Option[A]])
                                             (g: A => F[Either[Error, B]]): F[Either[Error, Option[B]]] =
    val eitherT =
      for
        exists <- EitherT(Files[F].exists(path).asError)
        value <-
          if exists then
            for
              value <- EitherT(Resource.fromAutoCloseable[F, JPEMParser](PEMParser[F](path)).use(f).asError)
              value <- value match
                case Some(value) => EitherT(g(value)).map(_.some)
                case _ => none[B].rLiftET[F, Error]
            yield
              value
          else
            none[B].rLiftET[F, Error]
      yield
        value
    eitherT.value

  private def writePem[F[_] : {Sync, Files}](path: Path)(f: JJcaPEMWriter => F[Unit]): F[Either[Error, Unit]] =
    val eitherT =
      for
        _ <- path.parent.fold(().rLiftET[F, Error])(parent => EitherT(Files[F].createDirectories(parent).asError))
        _ <- EitherT(Resource.fromAutoCloseable[F, JJcaPEMWriter](JcaPEMWriter[F](path)).use(f).asError)
      yield
        ()
    eitherT.value

  private def fetchPem[F[_]: {Sync, Files}, A](label: String,
                                               read: F[Either[Error, Option[A]]],
                                               write: A => F[Either[Error, Unit]],
                                               source: F[Either[Error, A]]): F[Either[Error, A]] =
    val eitherT =
      for
        value <- EitherT(fetch(Source(read, write), Source.read(source.map(_.map(_.some)))))
        value <- value.toRight(OptionEmpty.label(label)).eLiftET[F]
      yield
        value
    eitherT.value

  private def readPemKeyPair[F[_]: {Sync, Files}](path: Path, provider: Option[Provider | JProvider] = None)
  : F[Either[Error, Option[KeyPair]]] =
    readPem[F, AnyRef, KeyPair](path)(_.readObjectF[F]) { pemKeyPair =>
      val eitherT =
        for
          pemKeyPair <- typed[PEMKeyPair](pemKeyPair).eLiftET[F]
          keyPair <- EitherT(JcaPEMKeyConverter(provider = provider).getKeyPairF[F](pemKeyPair).asError)
        yield
          keyPair
      eitherT.value
    }

  private def writePemKeyPair[F[_] : {Sync, Files}](path: Path)(keyPair: KeyPair): F[Either[Error, Unit]] =
    writePem[F](path)(_.writeObjectF[F](keyPair))

  def fetchKeyPair[F[_]: {Sync, Files}](path: Path, provider: Option[Provider | JProvider] = None)
                                       (source: F[Either[Error, KeyPair]]): F[Either[Error, KeyPair]] =
    fetchPem[F, KeyPair]("keyPair", readPemKeyPair[F](path, provider), writePemKeyPair[F](path), source)

  private def readX509Certificates[F[_]: {Sync, Files}](path: Path, provider: Option[Provider | JProvider] = None)
  : F[Either[Error, Option[NonEmptyList[X509Certificate]]]] =
    readPem[F, NonEmptyList[AnyRef], NonEmptyList[X509Certificate]](path) { parser =>
      Monad[F].tailRecM[List[AnyRef], Option[NonEmptyList[AnyRef]]](Nil)(acc => parser.readObjectF[F].map {
        case Some(obj) => (obj :: acc).asLeft
        case _ => acc.reverse match
          case head :: tail => NonEmptyList(head, tail).some.asRight
          case _ => none[NonEmptyList[AnyRef]].asRight
      })
    } { x509Certificates =>
      val converter = JcaX509CertificateConverter(provider = provider)
      x509Certificates.traverse {
        case x509Certificate: X509Certificate => x509Certificate.rLiftET[F, Error]
        case certHolder: X509CertificateHolder => EitherT(converter.getCertificateF[F](certHolder).asError)
        case obj => WrongClassTag[X509Certificate](obj).lLiftET[F, X509Certificate]
      }.value
    }

  private def writeX509Certificates[F[_]: {Sync, Files}](path: Path)(certificates: NonEmptyList[X509Certificate])
  : F[Either[Error, Unit]] =
    writePem[F](path)(writer => Monad[F].tailRecM[List[X509Certificate], Unit](certificates.toList) {
      case head :: tail => writer.writeObjectF[F](head).as(tail.asLeft[Unit])
      case _ => ().asRight[List[X509Certificate]].pure[F]
    }.flatMap(_ => writer.flushF[F]))

  def fetchX509Certificates[F[_]: {Sync, Files}](path: Path, provider: Option[Provider | JProvider] = None)
                                                (source: F[Either[Error, NonEmptyList[X509Certificate]]])
  : F[Either[Error, NonEmptyList[X509Certificate]]] =
    fetchPem[F, NonEmptyList[X509Certificate]]("x509Certificates", readX509Certificates[F](path, provider),
      writeX509Certificates[F](path), source)
end openssl
