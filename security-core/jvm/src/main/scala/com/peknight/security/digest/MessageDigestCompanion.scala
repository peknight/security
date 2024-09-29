package com.peknight.security.digest

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.provider.Provider
import com.peknight.security.syntax.messageDigest.{digestF, updateF}
import scodec.bits.ByteVector

import java.security.{MessageDigest as JMessageDigest, Provider as JProvider}

trait MessageDigestCompanion:
  def getInstance[F[_]: Sync](algorithm: MessageDigestAlgorithm, provider: Option[Provider | JProvider] = None)
  : F[JMessageDigest] =
    Sync[F].blocking {
      provider match
        case Some(provider: Provider) => JMessageDigest.getInstance(algorithm.algorithm, provider.name)
        case Some(provider: JProvider) => JMessageDigest.getInstance(algorithm.algorithm, provider)
        case _ => JMessageDigest.getInstance(algorithm.algorithm)
    }

  def digest[F[_]: Sync](algorithm: MessageDigestAlgorithm, input: ByteVector,
                         provider: Option[Provider | JProvider] = None): F[ByteVector] =
    for
      messageDigest <- getInstance[F](algorithm, provider)
      _ <- messageDigest.updateF[F](input)
      output <- messageDigest.digestF[F]
    yield
      output
end MessageDigestCompanion
