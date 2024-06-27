package com.peknight.security.crypto

import cats.effect.Sync
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm

import javax.crypto.SecretKeyFactory as JSecretKeyFactory

object SecretKeyFactory:
  def getInstance[F[_]: Sync](algorithm: SecretKeyFactoryAlgorithm): F[JSecretKeyFactory] =
    Sync[F].blocking(JSecretKeyFactory.getInstance(algorithm.algorithm))
end SecretKeyFactory
