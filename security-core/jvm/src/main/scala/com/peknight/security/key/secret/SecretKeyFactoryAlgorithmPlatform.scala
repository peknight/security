package com.peknight.security.key.secret

import cats.effect.Sync
import com.peknight.security.provider.Provider

import java.security.Provider as JProvider
import java.security.spec.KeySpec
import javax.crypto.{SecretKey, SecretKeyFactory as JSecretKeyFactory}

trait SecretKeyFactoryAlgorithmPlatform { self: SecretKeyFactoryAlgorithm =>
  def getSecretKeyFactory[F[_]: Sync](provider: Option[Provider | JProvider] = None): F[JSecretKeyFactory] =
    SecretKeyFactory.getInstance[F](self, provider)

  def generateSecret[F[_]: Sync](keySpec: KeySpec, provider: Option[Provider | JProvider] = None): F[SecretKey] =
    SecretKeyFactory.generateSecret[F](self, keySpec, provider)
}
