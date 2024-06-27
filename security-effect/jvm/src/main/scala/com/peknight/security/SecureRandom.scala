package com.peknight.security

import java.security.SecureRandom as JSecureRandom
import cats.effect.Sync

object SecureRandom:
  def getInstanceStrong[F[_]: Sync]: F[JSecureRandom] = Sync[F].blocking(JSecureRandom.getInstanceStrong)
  def apply[F[_]: Sync]: F[JSecureRandom] = Sync[F].blocking(new JSecureRandom())
end SecureRandom
