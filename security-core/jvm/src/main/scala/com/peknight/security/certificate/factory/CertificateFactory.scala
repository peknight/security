package com.peknight.security.certificate.factory

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.io.ByteArrayInputStream
import com.peknight.security.provider.Provider
import com.peknight.security.syntax.certificateFactory.generateCertificateF
import scodec.bits.ByteVector

import java.io.InputStream
import java.security.Provider as JProvider
import java.security.cert.{Certificate, CertificateFactory as JCertificateFactory}

object CertificateFactory:
  def generateCertificateFromBytes[F[_]: Sync](typ: CertificateFactoryType, bytes: ByteVector,
                                               provider: Option[Provider | JProvider] = None): F[Certificate] =
    generateCertificate[F](typ, ByteArrayInputStream(bytes), provider)

  def getInstance[F[_]: Sync](typ: CertificateFactoryType, provider: Option[Provider | JProvider] = None): F[JCertificateFactory] =
    Sync[F].blocking {
      provider match
        case Some(provider: Provider) => JCertificateFactory.getInstance(typ.certFactoryType, provider.name)
        case Some(provider: JProvider) => JCertificateFactory.getInstance(typ.certFactoryType, provider)
        case _ => JCertificateFactory.getInstance(typ.certFactoryType)
    }
  def generateCertificate[F[_]: Sync](typ: CertificateFactoryType, inStream: InputStream,
                                      provider: Option[Provider | JProvider] = None): F[Certificate] =
    for
      certificateFactory <- getInstance[F](typ, provider)
      certificate <- certificateFactory.generateCertificateF[F](inStream)
    yield certificate
end CertificateFactory
