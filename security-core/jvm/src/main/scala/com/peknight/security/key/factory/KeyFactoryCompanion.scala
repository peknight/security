package com.peknight.security.key.factory

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.provider.Provider
import com.peknight.security.syntax.keyFactory.{generatePrivateF, generatePublicF}

import java.security.spec.KeySpec
import java.security.{PrivateKey, PublicKey, KeyFactory as JKeyFactory, Provider as JProvider}

trait KeyFactoryCompanion:
  def getInstance[F[_]: Sync](algorithm: KeyFactoryAlgorithm, provider: Option[Provider | JProvider]): F[JKeyFactory] =
    Sync[F].blocking {
      provider match
        case Some(provider: Provider) => JKeyFactory.getInstance(algorithm.algorithm, provider.name)
        case Some(provider: JProvider) => JKeyFactory.getInstance(algorithm.algorithm, provider)
        case _ => JKeyFactory.getInstance(algorithm.algorithm)
    }

  def generatePublic[F[_]: Sync](algorithm: KeyFactoryAlgorithm, keySpec: KeySpec,
                                 provider: Option[Provider | JProvider]): F[PublicKey] =
    for
      keyFactory <- getInstance[F](algorithm, provider)
      publicKey <- keyFactory.generatePublicF[F](keySpec)
    yield publicKey

  def generatePrivate[F[_]: Sync](algorithm: KeyFactoryAlgorithm, keySpec: KeySpec,
                                  provider: Option[Provider | JProvider]): F[PrivateKey] =
    for
      keyFactory <- getInstance[F](algorithm, provider)
      privateKey <- keyFactory.generatePrivateF[F](keySpec)
    yield privateKey
end KeyFactoryCompanion
