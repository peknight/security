package com.peknight.security.fs2.cipher.mode

import cats.effect.Sync
import cats.syntax.functor.*
import com.peknight.fs2.ext.syntax.stream.{chunkTimesN, evalMapChunksInitLast}
import com.peknight.security.cipher.Opmode.{Decrypt, Encrypt}
import com.peknight.security.cipher.padding.NoPadding
import com.peknight.security.cipher.{BlockCipher, CipherAlgorithm, Opmode, mode}
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm
import com.peknight.security.spec.SecretKeySpec
import fs2.{Chunk, Pipe}
import scodec.bits.ByteVector

import java.security.Key

object ECB:

  def crypto[F[_]: Sync](algorithm: CipherAlgorithm & BlockCipher, opmode: Opmode, key: Key): Pipe[F, Byte, Byte] =
    _.chunkTimesN(algorithm.blockSize).evalMapChunksInitLast {
      input => (algorithm / mode.ECB / NoPadding).keyCrypto[F](opmode, key, input = Some(input.toByteVector))
        .map(Chunk.byteVector)
    } {
      input => (algorithm / mode.ECB).keyCrypto[F](opmode, key, input = Some(input.toByteVector)).map(Chunk.byteVector)
    }

  def encrypt[F[_]: Sync](algorithm: CipherAlgorithm & BlockCipher, key: Key): Pipe[F, Byte, Byte] =
    crypto[F](algorithm, Encrypt, key)

  def decrypt[F[_]: Sync](algorithm: CipherAlgorithm & BlockCipher, key: Key): Pipe[F, Byte, Byte] =
    crypto[F](algorithm, Decrypt, key)

  def crypto[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm & BlockCipher, opmode: Opmode,
                         key: ByteVector): Pipe[F, Byte, Byte] =
    crypto[F](algorithm, opmode, SecretKeySpec(key, algorithm))

  def encrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm & BlockCipher, key: ByteVector)
  : Pipe[F, Byte, Byte] =
    crypto[F](algorithm, Encrypt, key)

  def decrypt[F[_]: Sync](algorithm: CipherAlgorithm & SecretKeyFactoryAlgorithm & BlockCipher, key: ByteVector)
  : Pipe[F, Byte, Byte] =
    crypto[F](algorithm, Decrypt, key)
end ECB
