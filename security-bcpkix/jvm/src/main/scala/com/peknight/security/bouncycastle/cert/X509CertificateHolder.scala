package com.peknight.security.bouncycastle.cert

import cats.effect.Sync
import org.bouncycastle.cert.X509CertificateHolder as JX509CertificateHolder
import scodec.bits.ByteVector

object X509CertificateHolder:
  def apply[F[_]: Sync](certEncoding: ByteVector): F[JX509CertificateHolder] =
    Sync[F].blocking(JX509CertificateHolder(certEncoding.toArray))
end X509CertificateHolder
