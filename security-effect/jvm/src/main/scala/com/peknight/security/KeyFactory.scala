package com.peknight.security

import cats.effect.Sync
import com.peknight.security.key.factory.KeyFactoryAlgorithm

import java.security.KeyFactory as JKeyFactory

object KeyFactory:
  def getInstance[F[_]: Sync](algorithm: KeyFactoryAlgorithm): F[JKeyFactory] =
    Sync[F].blocking(JKeyFactory.getInstance(algorithm.algorithm))
end KeyFactory
