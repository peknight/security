package com.peknight.security.cert.syntax

import cats.effect.kernel.Sync

import java.io.InputStream
import java.security.cert.{Certificate, CertificateFactory}

trait CertificateFactorySyntax:
  extension (certificateFactory: CertificateFactory)
    def generateCertificateF[F[_]: Sync](inStream: InputStream): F[Certificate] =
      Sync[F].blocking(certificateFactory.generateCertificate(inStream))
  end extension
end CertificateFactorySyntax
object CertificateFactorySyntax extends CertificateFactorySyntax
