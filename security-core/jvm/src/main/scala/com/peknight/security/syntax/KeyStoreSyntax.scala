package com.peknight.security.syntax

import cats.effect.Sync

import java.io.InputStream
import java.security.cert.Certificate
import java.security.{Key, KeyStore}

trait KeyStoreSyntax:
  extension (keyStore: KeyStore)
    def loadF[F[_]: Sync](stream: InputStream, password: String): F[Unit] =
      Sync[F].blocking(keyStore.load(stream, password.toCharArray))
    def getKeyF[F[_]: Sync](alias: String, password: String): F[Option[Key]] =
      Sync[F].blocking(Option(keyStore.getKey(alias, password.toCharArray)))
    def getCertificateF[F[_]: Sync](alias: String): F[Option[Certificate]] =
      Sync[F].blocking(Option(keyStore.getCertificate(alias)))
  end extension
end KeyStoreSyntax
object KeyStoreSyntax extends KeyStoreSyntax
