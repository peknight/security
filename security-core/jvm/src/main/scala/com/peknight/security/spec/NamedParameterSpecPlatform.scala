package com.peknight.security.spec

import cats.effect.Sync
import com.peknight.security.provider.Provider

import java.security.{KeyPair, SecureRandom, Provider as JProvider}

trait NamedParameterSpecPlatform:
  def generateKeyPair[F[_]: Sync](random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None): F[KeyPair]
end NamedParameterSpecPlatform
