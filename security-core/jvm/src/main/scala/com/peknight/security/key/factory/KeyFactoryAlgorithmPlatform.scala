package com.peknight.security.key.factory

import cats.effect.Sync
import com.peknight.security.provider.Provider

import java.security.{PrivateKey, PublicKey, Provider as JProvider}
import java.security.spec.KeySpec

trait KeyFactoryAlgorithmPlatform { self: KeyFactoryAlgorithm =>
  def generatePublic[F[_]: Sync](keySpec: KeySpec, provider: Option[Provider | JProvider] = None): F[PublicKey] =
    KeyFactory.generatePublic[F](self, keySpec, provider)
  def generatePrivate[F[_]: Sync](keySpec: KeySpec, provider: Option[Provider | JProvider] = None): F[PrivateKey] =
    KeyFactory.generatePrivate[F](self, keySpec, provider)
}
