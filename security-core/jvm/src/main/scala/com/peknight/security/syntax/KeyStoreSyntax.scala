package com.peknight.security.syntax

import cats.data.NonEmptyList
import cats.effect.{Resource, Sync}
import com.peknight.fs2.io.ext.syntax.path.{toFileInputStream, toFileOutputStream}
import fs2.io.file.Path
import scodec.bits.ByteVector

import java.io.{FileInputStream, FileOutputStream, InputStream, OutputStream}
import java.security.cert.Certificate
import java.security.{Key, KeyStore, PrivateKey}

trait KeyStoreSyntax:
  extension (keyStore: KeyStore)
    def loadF[F[_]: Sync](stream: InputStream, password: String): F[Unit] =
      Sync[F].blocking(keyStore.load(stream, password.toCharArray))
    def loadNothing[F[_]: Sync]: F[Unit] = Sync[F].blocking(keyStore.load(null, null))
    def loadPath[F[_]: Sync](path: Path, password: String): F[Unit] =
      Resource.fromAutoCloseable[F, FileInputStream](path.toFileInputStream[F]).use(stream => loadF[F](stream, password))
    def storeF[F[_]: Sync](stream: OutputStream, password: String): F[Unit] =
      Sync[F].blocking(keyStore.store(stream, password.toCharArray))
    def storePath[F[_]: Sync](path: Path, password: String): F[Unit] =
      Resource.fromAutoCloseable[F, FileOutputStream](path.toFileOutputStream[F])
        .use(stream => storeF[F](stream, password))
    def getKeyF[F[_]: Sync](alias: String, password: String): F[Option[Key]] =
      Sync[F].blocking(Option(keyStore.getKey(alias, password.toCharArray)))
    def getCertificateF[F[_]: Sync](alias: String): F[Option[Certificate]] =
      Sync[F].blocking(Option(keyStore.getCertificate(alias)))
    def setKeyEntryF[F[_]: Sync](alias: String, key: PrivateKey, password: String, chain: NonEmptyList[Certificate])
    : F[Unit] =
      Sync[F].blocking(keyStore.setKeyEntry(alias, key, password.toCharArray, chain.toList.toArray))
    def setKeyEntryF[F[_]: Sync](alias: String, key: ByteVector, chain: NonEmptyList[Certificate])
    : F[Unit] =
      Sync[F].blocking(keyStore.setKeyEntry(alias, key.toArray, chain.toList.toArray))
  end extension
end KeyStoreSyntax
object KeyStoreSyntax extends KeyStoreSyntax
