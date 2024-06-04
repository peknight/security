package com.peknight.security.bouncycastle.openssl.jcajce.syntax

import cats.effect.Sync
import org.bouncycastle.openssl.jcajce.JcaPEMWriter

trait JcaPEMWriterSyntax:
  extension (writer: JcaPEMWriter)
    def writeObjectF[F[_]: Sync](obj: AnyRef): F[Unit] = Sync[F].blocking(writer.writeObject(obj))
  end extension
end JcaPEMWriterSyntax
object JcaPEMWriterSyntax extends JcaPEMWriterSyntax
