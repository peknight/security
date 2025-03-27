package com.peknight.security.syntax

import cats.effect.kernel.Sync

import java.io.InputStream
import java.security.cert.{Certificate, CertificateFactory}
import scala.jdk.CollectionConverters.*

trait CertificateFactorySyntax:
  extension (certificateFactory: CertificateFactory)
    def generateCertificateF[F[_]: Sync](inStream: InputStream): F[Certificate] =
      Sync[F].blocking(certificateFactory.generateCertificate(inStream))

    def generateCertificatesF[F[_] : Sync](inStream: InputStream): F[List[Certificate]] =
      Sync[F].blocking(certificateFactory.generateCertificates(inStream).asScala.toList)
  end extension
end CertificateFactorySyntax
object CertificateFactorySyntax extends CertificateFactorySyntax
