package com.peknight.security

import cats.effect.Sync

import java.security.{Provider, Security as JSecurity}

object Security:
  def addProvider[F[_]: Sync](provider: Provider): F[Int] = Sync[F].blocking(JSecurity.addProvider(provider))
end Security
