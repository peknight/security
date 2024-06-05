package com.peknight.security

import cats.effect.Sync
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm
import com.peknight.security.provider.Provider

import java.security.{KeyPairGenerator as JKeyPairGenerator, Provider as JProvider}

object KeyPairGenerator:
  def getInstance[F[_]: Sync](algorithm: KeyPairGeneratorAlgorithm): F[JKeyPairGenerator] =
    Sync[F].delay(JKeyPairGenerator.getInstance(algorithm.algorithm))

  def getInstance[F[_]: Sync](algorithm: KeyPairGeneratorAlgorithm, provider: Provider): F[JKeyPairGenerator] =
    Sync[F].delay(JKeyPairGenerator.getInstance(algorithm.algorithm, provider.name))

  def getInstance[F[_]: Sync](algorithm: KeyPairGeneratorAlgorithm, provider: JProvider): F[JKeyPairGenerator] =
    Sync[F].delay(JKeyPairGenerator.getInstance(algorithm.algorithm, provider))
end KeyPairGenerator
