package com.peknight.security.bouncycastle.jce.provider

import cats.effect.Sync
import com.peknight.security.provider.Provider
import org.bouncycastle.jce.provider.BouncyCastleProvider as JBouncyCastleProvider

object BouncyCastleProvider extends Provider:
  def name: String = "BC"
  def apply[F[_]: Sync]: F[JBouncyCastleProvider] = Sync[F].blocking(new JBouncyCastleProvider())
end BouncyCastleProvider
