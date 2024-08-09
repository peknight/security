package com.peknight.security.fs2.cipher.mode

import cats.effect.Sync
import cats.syntax.functor.*
import com.peknight.fs2.ext.syntax.stream.{chunkTimesN, evalScanChunksInitLast}
import com.peknight.security.cipher.padding.NoPadding
import com.peknight.security.cipher.{BlockCipher, CipherAlgorithm, Opmode, mode}
import com.peknight.security.crypto.Cipher
import com.peknight.security.crypto.spec.{IvParameterSpec, SecretKeySpec}
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm
import fs2.{Chunk, Pipe}
import scodec.bits.ByteVector

import java.security.Key
import javax.crypto.spec.IvParameterSpec as JIvParameterSpec

object CBC:
  def encrypt[F[_]: Sync](algorithm: CipherAlgorithm & BlockCipher, key: Key, ivps: JIvParameterSpec)
  : Pipe[F, Byte, Byte] =
    _.chunkTimesN(algorithm.blockSize).evalScanChunksInitLast[F, Byte, Byte, JIvParameterSpec](ivps) {
      (ivps, input) => Cipher.keyEncrypt[F](
        algorithm / mode.CBC / NoPadding, key, params = Some(ivps), input = Some(input.toByteVector)
      ).map(output => (IvParameterSpec(output.takeRight(algorithm.blockSize)), Chunk.byteVector(output)))
    } {
      (ivps, input) => Cipher.keyEncrypt[F](
        algorithm / mode.CBC, key, params = Some(ivps), input = Some(input.toByteVector)
      ).map(Chunk.byteVector)
    }

  def decrypt[F[_]: Sync](algorithm: CipherAlgorithm & BlockCipher, key: Key, ivps: JIvParameterSpec)
  : Pipe[F, Byte, Byte] =
    _.chunkTimesN(algorithm.blockSize).evalScanChunksInitLast[F, Byte, Byte, JIvParameterSpec](ivps) {
      (ivps, input) => Cipher.keyDecrypt[F](
          algorithm / mode.CBC / NoPadding, key, params = Some(ivps), input = Some(input.toByteVector)
        ).map(output => (IvParameterSpec(input.takeRight(algorithm.blockSize).toByteVector), Chunk.byteVector(output)))
    } {
      (ivps, input) => Cipher.keyDecrypt[F](
        algorithm / mode.CBC, key, params = Some(ivps), input = Some(input.toByteVector)
      ).map(Chunk.byteVector)
    }

  def encrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm & BlockCipher, key: ByteVector,
                          iv: ByteVector): Pipe[F, Byte, Byte] =
    encrypt[F](algorithm, SecretKeySpec(key, algorithm), IvParameterSpec(iv))

  def decrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm & BlockCipher, key: ByteVector,
                          iv: ByteVector): Pipe[F, Byte, Byte] =
    decrypt[F](algorithm, SecretKeySpec(key, algorithm), IvParameterSpec(iv))
end CBC
