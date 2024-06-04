package com.peknight.security.syntax

import cats.effect.Sync

import java.security.spec.AlgorithmParameterSpec
import java.security.{KeyPair, KeyPairGenerator, SecureRandom}

trait KeyPairGeneratorSyntax:
  extension (generator: KeyPairGenerator)
    def initializeF[F[_]: Sync](params: AlgorithmParameterSpec, random: SecureRandom): F[Unit] =
      Sync[F].blocking(generator.initialize(params, random))

    def generateKeyPairF[F[_]: Sync]: F[KeyPair] =
      Sync[F].blocking(generator.generateKeyPair())
  end extension
end KeyPairGeneratorSyntax
object KeyPairGeneratorSyntax extends KeyPairGeneratorSyntax
