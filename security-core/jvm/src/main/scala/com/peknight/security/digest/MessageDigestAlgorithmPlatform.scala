package com.peknight.security.digest

import cats.effect.Sync
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.security.{MessageDigest as JMessageDigest, Provider as JProvider}

trait MessageDigestAlgorithmPlatform { self: MessageDigestAlgorithm =>
  def getMessageDigest[F[_]: Sync](provider: Option[Provider | JProvider] = None): F[JMessageDigest] =
    MessageDigest.getInstance[F](self, provider)

  def digest[F[_]: Sync](input: ByteVector, provider: Option[Provider | JProvider] = None): F[ByteVector] =
    MessageDigest.digest[F](self, input, provider)
}
