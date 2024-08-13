package com.peknight.security.random

import cats.effect.Sync

import java.security.SecureRandom as JSecureRandom

object SecureRandom:
  def getInstanceStrong[F[_]: Sync]: F[JSecureRandom] = Sync[F].blocking(JSecureRandom.getInstanceStrong)
  def apply[F[_]: Sync]: F[JSecureRandom] = Sync[F].blocking(new JSecureRandom())
end SecureRandom
