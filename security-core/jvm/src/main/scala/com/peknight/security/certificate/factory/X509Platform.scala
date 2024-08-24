package com.peknight.security.certificate.factory

import cats.effect.Sync
import cats.syntax.functor.*
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.io.InputStream
import java.security.Provider as JProvider
import java.security.cert.X509Certificate

trait X509Platform extends CertificateFactoryTypePlatform { self: CertificateFactoryType =>
  override def generateCertificateFromBytes[F[+_]: Sync](bytes: ByteVector, provider: Option[Provider | JProvider] = None): F[X509Certificate] =
    super.generateCertificateFromBytes[F](bytes, provider).map(_.asInstanceOf[X509Certificate])

  override def generateCertificate[F[+_]: Sync](inStream: InputStream, provider: Option[Provider | JProvider] = None): F[X509Certificate] =
    super.generateCertificate[F](inStream, provider).map(_.asInstanceOf[X509Certificate])
}
