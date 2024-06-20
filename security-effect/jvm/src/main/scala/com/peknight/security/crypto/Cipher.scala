package com.peknight.security.crypto

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.cipher.Opmode.{Decrypt, Encrypt}
import com.peknight.security.cipher.{CipherAlgorithm, Opmode}
import com.peknight.security.crypto.spec.{IvParameterSpec, SecretKeySpec}
import com.peknight.security.crypto.syntax.cipher.{doFinalF, initF}
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.security.spec.AlgorithmParameterSpec
import java.security.{Key, Provider as JProvider}
import javax.crypto.Cipher as JCipher

object Cipher:
  def getInstance[F[_]: Sync](transformation: CipherAlgorithm): F[JCipher] =
    Sync[F].delay(JCipher.getInstance(transformation.transformation))
  def getInstance[F[_]: Sync](transformation: CipherAlgorithm, provider: Provider): F[JCipher] =
    Sync[F].delay(JCipher.getInstance(transformation.transformation, provider.name))
  def getInstance[F[_]: Sync](transformation: CipherAlgorithm, provider: JProvider): F[JCipher] =
    Sync[F].delay(JCipher.getInstance(transformation.transformation, provider))

  def crypto[F[_] : Sync](algorithm: CipherAlgorithm, key: Key, opmode: Opmode)(input: ByteVector): F[ByteVector] =
    for
      cipher <- getInstance[F](algorithm)
      _ <- cipher.initF[F](opmode, key)
      output <- cipher.doFinalF[F](input)
    yield output

  def encrypt[F[_] : Sync](algorithm: CipherAlgorithm, key: Key)(input: ByteVector): F[ByteVector] =
    crypto[F](algorithm, key, Encrypt)(input)

  def decrypt[F[_] : Sync](algorithm: CipherAlgorithm, key: Key)(input: ByteVector): F[ByteVector] =
    crypto[F](algorithm, key, Decrypt)(input)

  def crypto[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector, opmode: Opmode)
                        (input: ByteVector): F[ByteVector] =
    crypto[F](algorithm, SecretKeySpec(key, algorithm), opmode)(input)

  def encrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector)(input: ByteVector)
  : F[ByteVector] =
    crypto[F](algorithm, key, Encrypt)(input)

  def decrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector)(input: ByteVector)
  : F[ByteVector] =
    crypto[F](algorithm, key, Decrypt)(input)

  def crypto[F[_] : Sync](algorithm: CipherAlgorithm, key: Key, params: AlgorithmParameterSpec, opmode: Opmode)
                         (input: ByteVector): F[ByteVector] =
    for
      cipher <- getInstance[F](algorithm)
      _ <- cipher.initF[F](opmode, key, params)
      output <- cipher.doFinalF[F](input)
    yield output

  def encrypt[F[_] : Sync](algorithm: CipherAlgorithm, key: Key, params: AlgorithmParameterSpec)(input: ByteVector)
  : F[ByteVector] =
    crypto[F](algorithm, key, params, Encrypt)(input)

  def decrypt[F[_] : Sync](algorithm: CipherAlgorithm, key: Key, params: AlgorithmParameterSpec)(input: ByteVector)
  : F[ByteVector] =
    crypto[F](algorithm, key, params, Decrypt)(input)

  def crypto[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector, iv: ByteVector,
                         opmode: Opmode)(input: ByteVector): F[ByteVector] =
    crypto[F](algorithm, SecretKeySpec(key, algorithm), IvParameterSpec(iv), opmode)(input)

  def encrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector, iv: ByteVector)
                         (input: ByteVector): F[ByteVector] =
    crypto[F](algorithm, key, iv, Encrypt)(input)

  def decrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector, iv: ByteVector)
                         (input: ByteVector): F[ByteVector] =
    crypto[F](algorithm, key, iv, Decrypt)(input)
end Cipher
