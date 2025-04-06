package com.peknight.security.bouncycastle.pkix.syntax

import cats.effect.Sync
import org.bouncycastle.cert.X509CertificateHolder
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter

import java.security.cert.X509Certificate


trait JcaX509CertificateConverterSyntax:
  extension (converter: JcaX509CertificateConverter)
    def getCertificateF[F[_]: Sync](certHolder: X509CertificateHolder): F[X509Certificate] =
      Sync[F].blocking(converter.getCertificate(certHolder))
  end extension
end JcaX509CertificateConverterSyntax
object JcaX509CertificateConverterSyntax extends JcaX509CertificateConverterSyntax
