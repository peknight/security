package com.peknight.security.certificate.factory

import cats.effect.Sync
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.io.InputStream
import java.security.Provider as JProvider
import java.security.cert.{Certificate, CertificateFactory as JCertificateFactory}

trait CertificateFactoryTypePlatform { self: CertificateFactoryType =>
  def generateCertificateFromBytes[F[+_]: Sync](bytes: ByteVector, provider: Option[Provider | JProvider] = None)
  : F[Certificate] =
    CertificateFactory.generateCertificateFromBytes[F](self, bytes, provider)

  def getCertificateFactory[F[_]: Sync](provider: Option[Provider | JProvider] = None): F[JCertificateFactory] =
    CertificateFactory.getInstance[F](self, provider)

  def generateCertificate[F[+_]: Sync](inStream: InputStream, provider: Option[Provider | JProvider] = None)
  : F[Certificate] =
    CertificateFactory.generateCertificate[F](self, inStream, provider)
}
