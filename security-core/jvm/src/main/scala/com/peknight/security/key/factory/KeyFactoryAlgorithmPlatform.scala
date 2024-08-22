package com.peknight.security.key.factory

import cats.effect.Sync
import com.peknight.security.provider.Provider

import java.security.spec.KeySpec
import java.security.{PrivateKey, PublicKey, KeyFactory as JKeyFactory, Provider as JProvider}

trait KeyFactoryAlgorithmPlatform { self: KeyFactoryAlgorithm =>
  def getKeyFactory[F[_]: Sync](provider: Option[Provider | JProvider]): F[JKeyFactory] =
    KeyFactory.getInstance[F](self, provider)
  def generatePublic[F[_]: Sync](keySpec: KeySpec, provider: Option[Provider | JProvider] = None): F[PublicKey] =
    KeyFactory.generatePublic[F](self, keySpec, provider)
  def generatePrivate[F[_]: Sync](keySpec: KeySpec, provider: Option[Provider | JProvider] = None): F[PrivateKey] =
    KeyFactory.generatePrivate[F](self, keySpec, provider)
}
