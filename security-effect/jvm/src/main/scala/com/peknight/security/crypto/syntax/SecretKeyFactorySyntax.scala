package com.peknight.security.crypto.syntax

import cats.effect.Sync

import java.security.spec.KeySpec
import javax.crypto.{SecretKey, SecretKeyFactory}

trait SecretKeyFactorySyntax:
  extension (secretKeyFactory: SecretKeyFactory)
    def generateSecretF[F[_]: Sync](keySpec: KeySpec): F[SecretKey] =
      Sync[F].blocking(secretKeyFactory.generateSecret(keySpec))
  end extension
end SecretKeyFactorySyntax
object SecretKeyFactorySyntax extends SecretKeyFactorySyntax
