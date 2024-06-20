package com.peknight.security.fs2.cipher.mode

import cats.effect.Sync
import cats.syntax.functor.*
import com.peknight.fs2.ext.syntax.stream.{chunkTimesN, evalMapChunksInitLast}
import com.peknight.security.cipher.Opmode.{Decrypt, Encrypt}
import com.peknight.security.cipher.padding.NoPadding
import com.peknight.security.cipher.{BlockCipher, CipherAlgorithm, Opmode, mode}
import com.peknight.security.crypto.Cipher
import com.peknight.security.crypto.spec.SecretKeySpec
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm
import fs2.{Chunk, Pipe}
import scodec.bits.ByteVector

import java.security.Key

object ECB:

  def crypto[F[_]: Sync](algorithm: CipherAlgorithm & BlockCipher, key: Key, opmode: Opmode): Pipe[F, Byte, Byte] =
    _.chunkTimesN(algorithm.blockSize).evalMapChunksInitLast {
      input => Cipher.crypto(algorithm / mode.ECB / NoPadding, key, opmode)(input.toByteVector).map(Chunk.byteVector)
    } {
      input => Cipher.crypto(algorithm / mode.ECB, key, opmode)(input.toByteVector).map(Chunk.byteVector)
    }

  def encrypt[F[_]: Sync](algorithm: CipherAlgorithm & BlockCipher, key: Key): Pipe[F, Byte, Byte] =
    crypto[F](algorithm, key, Encrypt)

  def decrypt[F[_]: Sync](algorithm: CipherAlgorithm & BlockCipher, key: Key): Pipe[F, Byte, Byte] =
    crypto[F](algorithm, key, Decrypt)

  def crypto[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm & BlockCipher, key: ByteVector,
                         opmode: Opmode): Pipe[F, Byte, Byte] =
    crypto[F](algorithm, SecretKeySpec(key, algorithm), opmode)

  def encrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm & BlockCipher, key: ByteVector)
  : Pipe[F, Byte, Byte] =
    crypto[F](algorithm, key, Encrypt)

  def decrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm & BlockCipher, key: ByteVector)
  : Pipe[F, Byte, Byte] =
    crypto[F](algorithm, key, Decrypt)
end ECB
