package com.peknight.security.certificate.factory

import cats.effect.Sync
import cats.syntax.functor.*
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.io.InputStream
import java.security.Provider as JProvider
import java.security.cert.X509Certificate

trait X509Platform extends CertificateFactoryTypePlatform { self: CertificateFactoryType =>
  def generateX509CertificateFromBytes[F[_]: Sync](bytes: ByteVector, provider: Option[Provider | JProvider] = None)
  : F[X509Certificate] =
    generateCertificateFromBytes[F](bytes, provider).map(_.asInstanceOf[X509Certificate])

  def generateX509Certificate[F[_]: Sync](inStream: InputStream, provider: Option[Provider | JProvider] = None)
  : F[X509Certificate] =
    generateCertificate[F](inStream, provider).map(_.asInstanceOf[X509Certificate])
}
