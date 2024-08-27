package com.peknight.security.syntax

import cats.effect.Sync

import java.io.InputStream
import java.security.KeyStore

trait KeyStoreSyntax:
  extension (keyStore: KeyStore)
    def loadF[F[_]: Sync](stream: InputStream, password: String): F[Unit] =
      Sync[F].blocking(keyStore.load(stream, password.toCharArray))
  end extension
end KeyStoreSyntax
object KeyStoreSyntax extends KeyStoreSyntax
