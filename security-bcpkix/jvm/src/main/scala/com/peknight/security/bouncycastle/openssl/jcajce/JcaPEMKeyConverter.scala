package com.peknight.security.bouncycastle.openssl.jcajce

import cats.effect.Sync
import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter as JJcaPEMKeyConverter

import java.security.KeyPair

object JcaPEMKeyConverter:
  def getKeyPair[F[_]: Sync](pemKeyPair: PEMKeyPair): F[KeyPair] =
    Sync[F].blocking(new JJcaPEMKeyConverter().getKeyPair(pemKeyPair))
end JcaPEMKeyConverter
