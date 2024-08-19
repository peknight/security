package com.peknight.security.key.pair

import cats.effect.Sync
import com.peknight.security.provider.Provider

import java.security.spec.AlgorithmParameterSpec
import java.security.{KeyPair, SecureRandom, Provider as JProvider}

trait KeyPairGeneratorAlgorithmPlatform { self: KeyPairGeneratorAlgorithm =>
  def keySizeGenerateKeyPair[F[_]: Sync](keySize: Int, provider: Option[Provider | JProvider] = None,
                                         random: Option[SecureRandom] = None): F[KeyPair] =
    KeyPairGenerator.keySizeGenerateKeyPair[F](self, keySize, provider, random)

  def paramsGenerateKeyPair[F[_]: Sync](params: AlgorithmParameterSpec, provider: Option[Provider | JProvider] = None,
                                        random: Option[SecureRandom] = None): F[KeyPair] =
    KeyPairGenerator.paramsGenerateKeyPair[F](self, params, provider, random)
}
