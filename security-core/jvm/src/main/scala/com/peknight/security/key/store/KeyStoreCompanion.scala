package com.peknight.security.key.store

import cats.data.NonEmptyList
import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.provider.Provider
import com.peknight.security.syntax.keyStore.{loadF, loadPath, setKeyEntryF, storePath}
import fs2.io.file.Path

import java.security.cert.Certificate
import java.security.{PrivateKey, KeyStore as JKeyStore, Provider as JProvider}

trait KeyStoreCompanion:
  def apply[F[_]: Sync](`type`: KeyStoreType, alias: String, key: PrivateKey, password: String,
                        chain: NonEmptyList[Certificate], provider: Option[Provider | JProvider] = None): F[JKeyStore] =
    for
      keyStore <- getInstance[F](`type`, provider)
      _ <- keyStore.loadF[F](null, null)
      _ <- keyStore.setKeyEntryF[F](alias, key, password, chain)
    yield
      keyStore

  def loadPath[F[_]: Sync](`type`: KeyStoreType, path: Path, password: String,
                           provider: Option[Provider | JProvider] = None): F[JKeyStore] =
    for
      keyStore <- getInstance[F](`type`, provider)
      _ <- keyStore.loadPath[F](path, password)
    yield
      keyStore

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
