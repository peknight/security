package com.peknight.security.key.store

import cats.data.NonEmptyList
import cats.effect.Sync
import com.peknight.security.provider.Provider
import fs2.io.file.Path

import java.security.cert.Certificate
import java.security.{KeyStore as JKeyStore, Provider as JProvider}
import java.security.{PrivateKey, KeyStore as JKeyStore}

trait KeyStoreTypePlatform { self: KeyStoreType =>
  def apply[F[_]: Sync](alias: String, key: PrivateKey, password: String, chain: NonEmptyList[Certificate],
                        provider: Option[Provider | JProvider] = None): F[JKeyStore] =
    KeyStore[F](self, alias, key, password, chain, provider)
  def loadPath[F[_]: Sync](path: Path, password: String, provider: Option[Provider | JProvider] = None): F[JKeyStore] =
    KeyStore.loadPath[F](self, path, password, provider)
  def getKeyStore[F[_]: Sync](provider: Option[Provider | JProvider] = None): F[JKeyStore] =
    KeyStore.getInstance[F](self, provider)
}
