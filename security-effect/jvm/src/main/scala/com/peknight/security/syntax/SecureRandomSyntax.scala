package com.peknight.security.syntax

import cats.effect.Sync
import scodec.bits.ByteVector

import java.security.SecureRandom

trait SecureRandomSyntax:
  extension (secureRandom: SecureRandom)
    def generateSeedF[F[_]: Sync](numBytes: Int): F[ByteVector] =
      Sync[F].blocking(ByteVector(secureRandom.generateSeed(numBytes)))
  end extension
end SecureRandomSyntax
object SecureRandomSyntax extends SecureRandomSyntax
