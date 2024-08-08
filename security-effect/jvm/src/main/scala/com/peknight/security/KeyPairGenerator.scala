package com.peknight.security

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm
import com.peknight.security.provider.Provider
import com.peknight.security.syntax.keyPairGenerator.{generateKeyPairF, initializeF}

import java.security.{KeyPair, KeyPairGenerator as JKeyPairGenerator, Provider as JProvider}

object KeyPairGenerator:
  def getInstance[F[_]: Sync](algorithm: KeyPairGeneratorAlgorithm): F[JKeyPairGenerator] =
    Sync[F].delay(JKeyPairGenerator.getInstance(algorithm.algorithm))

  def getInstance[F[_]: Sync](algorithm: KeyPairGeneratorAlgorithm, provider: Provider): F[JKeyPairGenerator] =
    Sync[F].delay(JKeyPairGenerator.getInstance(algorithm.algorithm, provider.name))

  def getInstance[F[_]: Sync](algorithm: KeyPairGeneratorAlgorithm, provider: Option[Provider]): F[JKeyPairGenerator] =
    provider.fold(getInstance[F](algorithm))(getInstance[F](algorithm, _))

  def getInstance[F[_]: Sync](algorithm: KeyPairGeneratorAlgorithm, provider: JProvider): F[JKeyPairGenerator] =
    Sync[F].delay(JKeyPairGenerator.getInstance(algorithm.algorithm, provider))

  def generateKeyPair[F[_]: Sync](algorithm: KeyPairGeneratorAlgorithm, keySize: Int): F[KeyPair] =
    for
      keyPairGenerator <- getInstance[F](algorithm)
      _ <- keyPairGenerator.initializeF[F](keySize)
      keyPair <- keyPairGenerator.generateKeyPairF[F]
    yield keyPair

end KeyPairGenerator
