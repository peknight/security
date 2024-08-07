package com.peknight.security

import cats.effect.Sync
import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.provider.Provider

import java.security.KeyFactory as JKeyFactory

object KeyFactory:
  def getInstance[F[_]: Sync](algorithm: KeyFactoryAlgorithm): F[JKeyFactory] =
    Sync[F].blocking(JKeyFactory.getInstance(algorithm.algorithm))

  def getInstance[F[_]: Sync](algorithm: KeyFactoryAlgorithm, provider: Provider): F[JKeyFactory] =
    Sync[F].blocking(JKeyFactory.getInstance(algorithm.algorithm, provider.name))

  def getInstance[F[_]: Sync](algorithm: KeyFactoryAlgorithm, provider: Option[Provider]): F[JKeyFactory] =
    provider.fold(getInstance(algorithm))(getInstance(algorithm, _))

end KeyFactory
