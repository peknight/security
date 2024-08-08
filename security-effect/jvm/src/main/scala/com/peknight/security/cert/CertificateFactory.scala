package com.peknight.security.cert

import cats.effect.Sync
import com.peknight.security.certificate.factory.CertificateFactoryType

import java.security.cert.CertificateFactory as JCertificateFactory

object CertificateFactory:
  def getInstance[F[_]: Sync](typ: CertificateFactoryType): F[JCertificateFactory] =
    Sync[F].blocking(JCertificateFactory.getInstance(typ.certFactoryType))
end CertificateFactory
