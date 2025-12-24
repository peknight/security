package com.peknight.security.cipher.mode

import cats.effect.Sync
import cats.syntax.functor.*
import com.peknight.fs2.syntax.stream.{chunkTimesN, evalScanChunksInitLast}
import com.peknight.security.cipher.*
import com.peknight.security.cipher.padding.NoPadding
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm
import com.peknight.security.spec.{IvParameterSpec, SecretKeySpec}
import fs2.{Chunk, Pipe}
import scodec.bits.ByteVector

import java.security.Key
import javax.crypto.spec.IvParameterSpec as JIvParameterSpec

trait CBCCompanion:
  def encrypt[F[_]: Sync](algorithm: CipherAlgorithm & BlockCipher, key: Key, ivps: JIvParameterSpec)
  : Pipe[F, Byte, Byte] =
    _.chunkTimesN(algorithm.blockSize).evalScanChunksInitLast[F, Byte, Byte, JIvParameterSpec](ivps) {
      (ivps, input) =>
        (algorithm / CBC / NoPadding).keyEncrypt[F](key, input.toByteVector, params = Some(ivps))
          .map(output => (IvParameterSpec(output.takeRight(algorithm.blockSize)), Chunk.byteVector(output)))
    } {
      (ivps, input) => (algorithm / CBC).keyEncrypt[F](key, input.toByteVector, params = Some(ivps))
        .map(Chunk.byteVector)
    }

  def decrypt[F[_]: Sync](algorithm: CipherAlgorithm & BlockCipher, key: Key, ivps: JIvParameterSpec)
  : Pipe[F, Byte, Byte] =
    _.chunkTimesN(algorithm.blockSize).evalScanChunksInitLast[F, Byte, Byte, JIvParameterSpec](ivps) {
      (ivps, input) => Cipher.keyDecrypt[F](
          algorithm / CBC / NoPadding, key, input.toByteVector, params = Some(ivps)
        ).map(output => (IvParameterSpec(input.takeRight(algorithm.blockSize).toByteVector), Chunk.byteVector(output)))
    } {
      (ivps, input) => Cipher.keyDecrypt[F](
        algorithm / CBC, key, input.toByteVector, params = Some(ivps)
      ).map(Chunk.byteVector)
    }

  def encrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm & BlockCipher, key: ByteVector,
                          iv: ByteVector): Pipe[F, Byte, Byte] =
    encrypt[F](algorithm, SecretKeySpec(key, algorithm), IvParameterSpec(iv))

  def decrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm & BlockCipher, key: ByteVector,
                          iv: ByteVector): Pipe[F, Byte, Byte] =
    decrypt[F](algorithm, SecretKeySpec(key, algorithm), IvParameterSpec(iv))
end CBCCompanion
