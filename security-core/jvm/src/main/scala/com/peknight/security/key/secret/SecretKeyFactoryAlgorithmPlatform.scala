package com.peknight.security.key.secret

import cats.effect.Sync
import cats.syntax.applicative.*
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.provider.Provider
import com.peknight.security.random.SecureRandom
import com.peknight.security.spec.SecretKeySpec
import com.peknight.security.syntax.secureRandom.nextBytesF
import scodec.bits.ByteVector

import java.security.spec.KeySpec
import java.security.{Provider as JProvider, SecureRandom as JSecureRandom}
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec as JSecretKeySpec

trait SecretKeyFactoryAlgorithmPlatform { self: SecretKeyFactoryAlgorithm =>
  def secretKeySpec(key: ByteVector): JSecretKeySpec = SecretKeySpec(key, self)
  def keySizeGenerateKey[F[_]: Sync](keySize: Int, random: Option[JSecureRandom] = None): F[JSecretKeySpec] =
    for
      random <- random.fold(SecureRandom.getInstanceStrong[F])(_.pure[F])
      key <- random.nextBytesF(keySize / 8)
    yield secretKeySpec(key)
  def generateSecret[F[_]: Sync](keySpec: KeySpec, provider: Option[Provider | JProvider] = None): F[SecretKey] =
    SecretKeyFactory.generateSecret[F](self, keySpec, provider)
}
