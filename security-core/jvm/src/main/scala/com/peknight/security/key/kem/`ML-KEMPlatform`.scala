package com.peknight.security.key.kem

import cats.effect.Sync
import com.peknight.security.provider.Provider
import com.peknight.security.spec.{NamedParameterSpec, NamedParameterSpecPlatform}

import java.security.{KeyPair, SecureRandom, Provider as JProvider}

trait `ML-KEMPlatform` extends NamedParameterSpecPlatform { self: `ML-KEM-N` =>
  def generateKeyPair[F[_]: Sync](random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None): F[KeyPair] =
    `ML-KEM`.paramsGenerateKeyPair[F](NamedParameterSpec(self), random, provider)
}
