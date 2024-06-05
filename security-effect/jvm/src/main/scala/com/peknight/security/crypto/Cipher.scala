package com.peknight.security.crypto

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.cipher.CipherAlgorithm
import com.peknight.security.cipher.Opmode.{Decrypt, Encrypt}
import com.peknight.security.crypto.spec.{IvParameterSpec, SecretKeySpec}
import com.peknight.security.crypto.syntax.cipher.{doFinalF, initF}
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.security.Provider as JProvider
import javax.crypto.Cipher as JCipher

object Cipher:
  def getInstance[F[_]: Sync](transformation: CipherAlgorithm): F[JCipher] =
    Sync[F].delay(JCipher.getInstance(transformation.transformation))
  def getInstance[F[_]: Sync](transformation: CipherAlgorithm, provider: Provider): F[JCipher] =
    Sync[F].delay(JCipher.getInstance(transformation.transformation, provider.name))
  def getInstance[F[_]: Sync](transformation: CipherAlgorithm, provider: JProvider): F[JCipher] =
    Sync[F].delay(JCipher.getInstance(transformation.transformation, provider))

  def encrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector)(input: ByteVector)
  : F[ByteVector] =
    for
      cipher <- getInstance[F](algorithm)
      _ <- cipher.initF[F](Encrypt, SecretKeySpec(key, algorithm))
      output <- cipher.doFinalF[F](input)
    yield output

  def decrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector)(input: ByteVector)
  : F[ByteVector] =
    for
      cipher <- getInstance[F](algorithm)
      _ <- cipher.initF[F](Decrypt, SecretKeySpec(key, algorithm))
      output <- cipher.doFinalF[F](input)
    yield output

  def encrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector, iv: ByteVector)
                         (input: ByteVector): F[ByteVector] =
    for
      cipher <- getInstance[F](algorithm)
      _ <- cipher.initF[F](Encrypt, SecretKeySpec(key, algorithm), IvParameterSpec(iv))
      output <- cipher.doFinalF[F](input)
    yield output

  def decrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector, iv: ByteVector)
                         (input: ByteVector): F[ByteVector] =
    for
      cipher <- getInstance[F](algorithm)
      _ <- cipher.initF[F](Decrypt, SecretKeySpec(key, algorithm), IvParameterSpec(iv))
      output <- cipher.doFinalF[F](input)
    yield output
end Cipher
