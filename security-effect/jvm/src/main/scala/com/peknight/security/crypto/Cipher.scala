package com.peknight.security.crypto

import cats.effect.Sync
import com.peknight.security.cipher.CipherAlgorithm
import com.peknight.security.provider.Provider

import java.security.Provider as JProvider
import javax.crypto.Cipher as JCipher

object Cipher:
  def getInstance[F[_]: Sync](transformation: CipherAlgorithm): F[JCipher] =
    Sync[F].blocking(JCipher.getInstance(transformation.transformation))
  def getInstance[F[_]: Sync](transformation: CipherAlgorithm, provider: Provider): F[JCipher] =
    Sync[F].blocking(JCipher.getInstance(transformation.transformation, provider.name))
  def getInstance[F[_]: Sync](transformation: CipherAlgorithm, provider: JProvider): F[JCipher] =
    Sync[F].blocking(JCipher.getInstance(transformation.transformation, provider))
end Cipher
