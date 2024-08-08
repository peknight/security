package com.peknight.security.syntax

import cats.effect.Sync

import java.security.spec.AlgorithmParameterSpec
import java.security.{KeyPair, KeyPairGenerator, SecureRandom}

trait KeyPairGeneratorSyntax:
  extension (generator: KeyPairGenerator)
    def initializeF[F[_]: Sync](keySize: Int): F[Unit] =
      Sync[F].blocking(generator.initialize(keySize))

    def initializeF[F[_]: Sync](keySize: Int, random: SecureRandom): F[Unit] =
      Sync[F].blocking(generator.initialize(keySize, random))

    def initializeF[F[_]: Sync](keySize: Int, random: Option[SecureRandom]): F[Unit] =
      random.fold(initializeF[F](keySize))(initializeF[F](keySize, _))

    def initializeF[F[_]: Sync](params: AlgorithmParameterSpec): F[Unit] =
      Sync[F].blocking(generator.initialize(params))

    def initializeF[F[_]: Sync](params: AlgorithmParameterSpec, random: SecureRandom): F[Unit] =
      Sync[F].blocking(generator.initialize(params, random))

    def initializeF[F[_]: Sync](params: AlgorithmParameterSpec, random: Option[SecureRandom]): F[Unit] =
      random.fold(initializeF[F](params))(initializeF[F](params, _))

    def generateKeyPairF[F[_]: Sync]: F[KeyPair] =
      Sync[F].blocking(generator.generateKeyPair())
  end extension
end KeyPairGeneratorSyntax
object KeyPairGeneratorSyntax extends KeyPairGeneratorSyntax
