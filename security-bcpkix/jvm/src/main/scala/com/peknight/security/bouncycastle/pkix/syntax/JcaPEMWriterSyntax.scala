package com.peknight.security.bouncycastle.pkix.syntax

import cats.effect.Sync
import org.bouncycastle.openssl.jcajce.JcaPEMWriter

trait JcaPEMWriterSyntax:
  extension (writer: JcaPEMWriter)
    def writeObjectF[F[_]: Sync](obj: AnyRef): F[Unit] = Sync[F].blocking(writer.writeObject(obj))
    def flushF[F[_]: Sync]: F[Unit] = Sync[F].blocking(writer.flush())
  end extension
end JcaPEMWriterSyntax
object JcaPEMWriterSyntax extends JcaPEMWriterSyntax
