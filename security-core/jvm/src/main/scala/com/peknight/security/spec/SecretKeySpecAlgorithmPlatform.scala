package com.peknight.security.spec

import cats.effect.Sync
import cats.syntax.applicative.*
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.random.SecureRandom
import com.peknight.security.syntax.secureRandom.nextBytesF
import scodec.bits.ByteVector

import java.security.SecureRandom as JSecureRandom
import javax.crypto.spec.SecretKeySpec as JSecretKeySpec

trait SecretKeySpecAlgorithmPlatform { self: SecretKeySpecAlgorithm =>
  def secretKeySpec(key: ByteVector): JSecretKeySpec = SecretKeySpec(key, self)

  def keySizeGenerateKey[F[_]: Sync](keySize: Int, random: Option[JSecureRandom] = None): F[JSecretKeySpec] =
    for
      random <- random.fold(SecureRandom.getInstanceStrong[F])(_.pure[F])
      key <- random.nextBytesF(keySize / 8)
    yield secretKeySpec(key)
}
