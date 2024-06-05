package com.peknight.security.bouncycastle.openssl.jcajce.syntax

import cats.effect.Sync
import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter

import java.security.KeyPair

trait JcaPEMKeyConverterSyntax:
  extension (converter: JcaPEMKeyConverter)
    def getKeyPairF[F[_]: Sync](pemKeyPair: PEMKeyPair): F[KeyPair] = Sync[F].delay(converter.getKeyPair(pemKeyPair))
  end extension
end JcaPEMKeyConverterSyntax
object JcaPEMKeyConverterSyntax extends JcaPEMKeyConverterSyntax
