package com.peknight.security.signature

import cats.effect.Sync
import com.peknight.security.provider.Provider
import com.peknight.security.spec.{NamedParameterSpec, NamedParameterSpecPlatform}

import java.security.{KeyPair, SecureRandom, Provider as JProvider}

trait `ML-DSAPlatform` extends NamedParameterSpecPlatform { self: `ML-DSA-N` =>
  def generateKeyPair[F[_] : Sync](random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None): F[KeyPair] =
    `ML-DSA`.paramsGenerateKeyPair[F](NamedParameterSpec(self), random, provider)
}
