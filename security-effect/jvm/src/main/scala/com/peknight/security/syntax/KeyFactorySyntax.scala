package com.peknight.security.syntax

import cats.effect.Sync

import java.security.spec.KeySpec
import java.security.{KeyFactory, PrivateKey, PublicKey}

trait KeyFactorySyntax:
  extension (keyFactory: KeyFactory)
    def generatePublicF[F[_]: Sync](keySpec: KeySpec): F[PublicKey] =
      Sync[F].blocking(keyFactory.generatePublic(keySpec))

    def generatePrivateF[F[_]: Sync](keySpec: KeySpec): F[PrivateKey] =
      Sync[F].blocking(keyFactory.generatePrivate(keySpec))
  end extension
end KeyFactorySyntax
object KeyFactorySyntax extends KeyFactorySyntax
