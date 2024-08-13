package com.peknight.security.key.secret

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.provider.Provider
import com.peknight.security.syntax.secretKeyFactory.generateSecretF

import java.security.Provider as JProvider
import java.security.spec.KeySpec
import javax.crypto.{SecretKey, SecretKeyFactory as JSecretKeyFactory}

object SecretKeyFactory:
  def getInstance[F[_]: Sync](algorithm: SecretKeyFactoryAlgorithm, provider: Option[Provider | JProvider] = None)
  : F[JSecretKeyFactory] =
    Sync[F].blocking {
      provider match
        case Some(provider: Provider) => JSecretKeyFactory.getInstance(algorithm.algorithm, provider.name)
        case Some(provider: JProvider) => JSecretKeyFactory.getInstance(algorithm.algorithm, provider)
        case _ => JSecretKeyFactory.getInstance(algorithm.algorithm)
    }

  def generateSecret[F[_]: Sync](algorithm: SecretKeyFactoryAlgorithm, keySpec: KeySpec,
                                 provider: Option[Provider | JProvider] = None): F[SecretKey] =
    for
      keyFactory <- getInstance[F](algorithm, provider)
      key <- keyFactory.generateSecretF[F](keySpec)
    yield key
end SecretKeyFactory
