package com.peknight.security.syntax

import cats.effect.Sync

import java.security.spec.AlgorithmParameterSpec
import java.security.{KeyPair, KeyPairGenerator, SecureRandom}

trait KeyPairGeneratorSyntax:
  extension (generator: KeyPairGenerator)
    def keySizeInitialize[F[_]: Sync](keySize: Int, random: Option[SecureRandom] = None): F[Unit] =
      Sync[F].blocking(random.fold(generator.initialize(keySize))(generator.initialize(keySize, _)))

    def paramsInitialize[F[_]: Sync](params: AlgorithmParameterSpec, random: Option[SecureRandom] = None): F[Unit] =
      Sync[F].blocking(random.fold(generator.initialize(params))(generator.initialize(params, _)))

    def generateKeyPairF[F[_]: Sync]: F[KeyPair] =
      Sync[F].blocking(generator.generateKeyPair())
  end extension
end KeyPairGeneratorSyntax
object KeyPairGeneratorSyntax extends KeyPairGeneratorSyntax
