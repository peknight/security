package com.peknight.security.crypto.spec

import cats.effect.Sync
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm
import scodec.bits.ByteVector

import javax.crypto.spec.SecretKeySpec as JSecretKeySpec

object SecretKeySpec:
  def apply[F[_]: Sync](key: ByteVector, algorithm: SecretKeyFactoryAlgorithm): F[JSecretKeySpec] =
    Sync[F].blocking(new JSecretKeySpec(key.toArray, algorithm.algorithm))
end SecretKeySpec
