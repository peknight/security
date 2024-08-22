package com.peknight.security.key.pair

import cats.effect.Sync
import com.peknight.security.provider.Provider

import java.security.spec.AlgorithmParameterSpec
import java.security.{KeyPair, SecureRandom, KeyPairGenerator as JKeyPairGenerator, Provider as JProvider}

trait KeyPairGeneratorAlgorithmPlatform { self: KeyPairGeneratorAlgorithm =>
  def getKeyPairGenerator[F[_]: Sync](provider: Option[Provider | JProvider] = None): F[JKeyPairGenerator] =
    KeyPairGenerator.getInstance[F](self, provider)
  def keySizeGenerateKeyPair[F[_]: Sync](keySize: Int, random: Option[SecureRandom] = None,
                                         provider: Option[Provider | JProvider] = None): F[KeyPair] =
    KeyPairGenerator.keySizeGenerateKeyPair[F](self, keySize, random, provider)

  def paramsGenerateKeyPair[F[_]: Sync](params: AlgorithmParameterSpec, random: Option[SecureRandom] = None,
                                        provider: Option[Provider | JProvider] = None): F[KeyPair] =
    KeyPairGenerator.paramsGenerateKeyPair[F](self, params, random, provider)
}
