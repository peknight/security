package com.peknight.security.syntax

import cats.effect.Sync
import com.peknight.security.key.pair.{KeyPairGenerator, KeyPairGeneratorAlgorithm}
import com.peknight.security.provider.Provider

import java.security.spec.AlgorithmParameterSpec
import java.security.{KeyPair, SecureRandom, Provider as JProvider}

trait AlgorithmParameterSpecSyntax:
  extension (params: AlgorithmParameterSpec)
    def generateKeyPair[F[_]: Sync](algorithm: KeyPairGeneratorAlgorithm, random: Option[SecureRandom] = None,
                                    provider: Option[Provider | JProvider] = None): F[KeyPair] =
      KeyPairGenerator.paramsGenerateKeyPair[F](algorithm, params, random, provider)
  end extension
end AlgorithmParameterSpecSyntax
object AlgorithmParameterSpecSyntax extends AlgorithmParameterSpecSyntax
