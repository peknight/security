package com.peknight.security.cert

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.cert.syntax.certificateFactory.generateCertificateF
import com.peknight.security.certificate.factory.CertificateFactoryType
import com.peknight.security.provider.Provider

import java.io.InputStream
import java.security.Provider as JProvider
import java.security.cert.{Certificate, CertificateFactory as JCertificateFactory}

object CertificateFactory:
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
