package com.peknight.security.syntax

import cats.effect.Sync
import scodec.bits.ByteVector

import java.security.SecureRandom

trait SecureRandomSyntax:
  extension (secureRandom: SecureRandom)
    def generateSeedF[F[_]: Sync](numBytes: Int): F[ByteVector] =
      Sync[F].blocking(ByteVector(secureRandom.generateSeed(numBytes)))

    def nextBytesF[F[_]: Sync](n: Int): F[ByteVector] =
      Sync[F].delay {
        val bytes = new Array[Byte](0 max n)
        secureRandom.nextBytes(bytes)
        ByteVector(bytes)
      }
  end extension
end SecureRandomSyntax
object SecureRandomSyntax extends SecureRandomSyntax
