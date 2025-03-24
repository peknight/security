package com.peknight.security.bouncycastle.pkix.syntax

import cats.effect.Sync
import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter

import java.security.KeyPair

trait JcaPEMKeyConverterSyntax:
  extension (converter: JcaPEMKeyConverter)
    def getKeyPairF[F[_]: Sync](pemKeyPair: PEMKeyPair): F[KeyPair] = Sync[F].blocking(converter.getKeyPair(pemKeyPair))
  end extension
end JcaPEMKeyConverterSyntax
object JcaPEMKeyConverterSyntax extends JcaPEMKeyConverterSyntax
