package com.peknight.security.key.store

import cats.effect.Sync
import cats.syntax.flatMap.*
import com.peknight.security.provider.Provider

import java.security.{KeyStore as JKeyStore, Provider as JProvider}

trait KeyStoreCompanion:
  def getInstance[F[_]: Sync](`type`: KeyStoreType, provider: Option[Provider | JProvider] = None): F[JKeyStore] =
    Sync[F].blocking {
      provider match
        case Some(provider: Provider) => JKeyStore.getInstance(`type`.keyStoreType, provider.name)
        case Some(provider: JProvider) => JKeyStore.getInstance(`type`.keyStoreType, provider)
        case _ => JKeyStore.getInstance(`type`.keyStoreType)
    }

  def getDefaultType[F[_]: Sync]: F[String] = Sync[F].blocking(JKeyStore.getDefaultType)

  def getDefaultInstance[F[_]: Sync](provider: Option[Provider | JProvider] = None): F[JKeyStore] =
    getDefaultType.flatMap { `type` =>
      Sync[F].blocking {
        provider match
          case Some(provider: Provider) => JKeyStore.getInstance(`type`, provider.name)
          case Some(provider: JProvider) => JKeyStore.getInstance(`type`, provider)
          case _ => JKeyStore.getInstance(`type`)
      }
    }
end KeyStoreCompanion
