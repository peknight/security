package com.peknight.security.key.pair

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.provider.Provider
import com.peknight.security.syntax.keyPairGenerator.{generateKeyPairF, keySizeInitialize, paramsInitialize}

import java.security.spec.AlgorithmParameterSpec
import java.security.{KeyPair, SecureRandom, KeyPairGenerator as JKeyPairGenerator, Provider as JProvider}

object KeyPairGenerator:
  def getInstance[F[_]: Sync](algorithm: KeyPairGeneratorAlgorithm, provider: Option[Provider | JProvider] = None)
  : F[JKeyPairGenerator] =
    Sync[F].blocking {
      provider match
        case Some(provider: Provider) => JKeyPairGenerator.getInstance(algorithm.algorithm, provider.name)
        case Some(provider: JProvider) => JKeyPairGenerator.getInstance(algorithm.algorithm, provider)
        case _ => JKeyPairGenerator.getInstance(algorithm.algorithm)
    }

  def keySizeGenerateKeyPair[F[_]: Sync](algorithm: KeyPairGeneratorAlgorithm, keySize: Int,
                                         provider: Option[Provider | JProvider] = None,
                                         random: Option[SecureRandom] = None): F[KeyPair] =
    for
      keyPairGenerator <- getInstance[F](algorithm, provider)
      _ <- keyPairGenerator.keySizeInitialize[F](keySize, random)
      keyPair <- keyPairGenerator.generateKeyPairF[F]
    yield keyPair

  def paramsGenerateKeyPair[F[_]: Sync](algorithm: KeyPairGeneratorAlgorithm, params: AlgorithmParameterSpec,
                                        provider: Option[Provider | JProvider] = None,
                                        random: Option[SecureRandom] = None): F[KeyPair] =
    for
      keyPairGenerator <- getInstance[F](algorithm, provider)
      _ <- keyPairGenerator.paramsInitialize[F](params, random)
      keyPair <- keyPairGenerator.generateKeyPairF[F]
    yield keyPair
end KeyPairGenerator
