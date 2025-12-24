package com.peknight.security.pkix

import cats.Applicative
import cats.data.EitherT
import cats.effect.Sync
import cats.parse.Parser
import cats.syntax.eq.*
import cats.syntax.functor.*
import com.peknight.cats.syntax.eitherT.eLiftET
import com.peknight.codec.base.Base64
import com.peknight.codec.error.DecodingFailure
import com.peknight.codec.{Decoder, Encoder}
import com.peknight.error.Error
import com.peknight.error.syntax.applicativeError.asET
import com.peknight.security.certificate.factory.X509
import com.peknight.security.error.PEMLabelNotMatch
import com.peknight.security.provider.Provider
import com.peknight.validation.std.either.{isTrue, typed}
import scodec.bits.ByteVector

import java.security.Provider as JProvider
import java.security.cert.{Certificate, X509Certificate}
import scala.reflect.ClassTag

case class PEMObject(label: String, headers: List[PEMHeader], data: Base64):
  def lines: List[String] =
    s"-----BEGIN $label-----" ::
      headers.map(_.toString) :::
      data.value.grouped(64).toList :::
      s"-----END $label-----" :: Nil
end PEMObject
object PEMObject:
  case class BeginLine(label: String)
  case class EndLine(label: String)

  val beginLineParser: Parser[BeginLine] =
    Parser.charsWhile(ch => ch != '-')
      .map(label => BeginLine(label.trim))
      .between(Parser.string("-----BEGIN "), Parser.string("-----"))
  val endLineParser: Parser[EndLine] =
    Parser.charsWhile(ch => ch != '-')
      .map(label => EndLine(label.trim))
      .between(Parser.string("-----END "), Parser.string("-----"))

  val certificateLabel: String = "CERTIFICATE"
  private val labelMap: Map[Class[?], String] = Map(
    classOf[Certificate] -> certificateLabel,
  )

  def label(clazz: Class[?]): Option[String] =
    labelMap.filter((key, label) => key.isAssignableFrom(clazz)).values.headOption

  def label[A](using classTag: ClassTag[A]): Option[String] = label(classTag.runtimeClass)

  given encodeCertificate[F[_]: Applicative, Cert <: Certificate]: Encoder[F, PEMObject, Cert] =
    Encoder.applicative[F, PEMObject, Cert](a =>
      PEMObject(certificateLabel, Nil, Base64.fromByteVector(ByteVector(a.getEncoded)))
    )

  def decodeX509Certificate[F[_]: Sync](provider: Option[Provider | JProvider] = None)
  : Decoder[F, PEMObject, X509Certificate] =
    Decoder.instance[F, PEMObject, X509Certificate] { t =>
      val eitherT: EitherT[F, Error, X509Certificate] =
        for
          _ <- isTrue(t.label === certificateLabel, PEMLabelNotMatch(certificateLabel, t.label)).eLiftET[F]
          bytes <- EitherT(t.data.decode[F])
          certificate <- X509.generateCertificateFromBytes[F](bytes, provider).asET
          x509Certificate <- typed[X509Certificate](certificate).eLiftET[F]
        yield
          x509Certificate
      eitherT.value.map(_.left.map(DecodingFailure.apply))
    }
end PEMObject
