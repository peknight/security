package com.peknight.security.syntax

import cats.effect.Sync
import com.peknight.security.cipher.{Cipher, Opmode}
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.security.spec.AlgorithmParameterSpec
import java.security.{AlgorithmParameters, Key, SecureRandom, Provider as JProvider}

trait KeySyntax:
  extension (key: Key)
    def crypto[F[_]: Sync](opmode: Opmode, input: ByteVector,
                           params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                           aad: Option[ByteVector] = None,
                           random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
    : F[ByteVector] =
      Cipher.keyAlgorithmCrypto[F](opmode, key, input, params, aad, random, provider)

    def encrypt[F[_]: Sync](input: ByteVector, params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                            aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                            provider: Option[Provider | JProvider] = None): F[ByteVector] =
      Cipher.keyAlgorithmEncrypt[F](key, input, params, aad, random, provider)

    def decrypt[F[_]: Sync](input: ByteVector, params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                            aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                            provider: Option[Provider | JProvider] = None): F[ByteVector] =
      Cipher.keyAlgorithmDecrypt[F](key, input, params, aad, random, provider)
  end extension
end KeySyntax
object KeySyntax extends KeySyntax
