package com.peknight.security.cipher

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm
import com.peknight.security.provider.Provider
import com.peknight.security.syntax.cipher.*
import scodec.bits.ByteVector

import java.security.cert.Certificate
import java.security.spec.AlgorithmParameterSpec
import java.security.{AlgorithmParameters, Key, SecureRandom, Provider as JProvider}
import javax.crypto.Cipher as JCipher

trait CipherCompanion:

  def getInstance[F[_]: Sync](transformation: CipherAlgorithm, provider: Option[Provider | JProvider] = None): F[JCipher] =
    Sync[F].blocking {
      provider match
        case Some(provider: Provider) => JCipher.getInstance(transformation.transformation, provider.name)
        case Some(provider: JProvider) => JCipher.getInstance(transformation.transformation, provider)
        case _ => JCipher.getInstance(transformation.transformation)
    }

  def keyCrypto[F[_]: Sync](transformation: CipherAlgorithm, opmode: Opmode, key: Key, input: ByteVector,
                            params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                            random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    for
      cipher <- getInstance[F](transformation, provider)
      _ <- cipher.keyInit[F](opmode, key, params, random)
      output <- cipher.doFinalF[F](input)
    yield output

  def keyEncrypt[F[_]: Sync](transformation: CipherAlgorithm, key: Key, input: ByteVector,
                             params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                             random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    for
      cipher <- getInstance[F](transformation, provider)
      _ <- cipher.keyInitEncrypt[F](key, params, random)
      output <- cipher.doFinalF[F](input)
    yield output

  def keyDecrypt[F[_]: Sync](transformation: CipherAlgorithm, key: Key, input: ByteVector,
                             params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                             random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    for
      cipher <- getInstance[F](transformation, provider)
      _ <- cipher.keyInitDecrypt[F](key, params, random)
      output <- cipher.doFinalF[F](input)
    yield output

  def rawKeyCrypto[F[_]: Sync](transformation: CipherAlgorithm & SecretKeyFactoryAlgorithm, opmode: Opmode,
                               key: ByteVector, input: ByteVector, iv: Option[ByteVector] = None,
                               random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    for
      cipher <- getInstance[F](transformation, provider)
      _ <- cipher.rawKeyInit[F](transformation, opmode, key, iv, random)
      output <- cipher.doFinalF[F](input)
    yield output

  def rawKeyEncrypt[F[_]: Sync](transformation: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector,
                                input: ByteVector, iv: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                                provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    for
      cipher <- getInstance[F](transformation, provider)
      _ <- cipher.rawKeyInitEncrypt[F](transformation, key, iv, random)
      output <- cipher.doFinalF[F](input)
    yield output

  def rawKeyDecrypt[F[_]: Sync](transformation: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector,
                                input: ByteVector, iv: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                                provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    for
      cipher <- getInstance[F](transformation, provider)
      _ <- cipher.rawKeyInitDecrypt[F](transformation, key, iv, random)
      output <- cipher.doFinalF[F](input)
    yield output

  def certificateCrypto[F[_]: Sync](transformation: CipherAlgorithm, opmode: Opmode, certificate: Certificate,
                                    input: ByteVector, random: Option[SecureRandom] = None,
                                    provider: Option[Provider | JProvider] = None): F[ByteVector] =
    for
      cipher <- getInstance[F](transformation, provider)
      _ <- cipher.certificateInit[F](opmode, certificate, random)
      output <- cipher.doFinalF[F](input)
    yield output

  def certificateEncrypt[F[_]: Sync](transformation: CipherAlgorithm, certificate: Certificate,
                                     input: ByteVector, random: Option[SecureRandom] = None,
                                     provider: Option[Provider | JProvider] = None): F[ByteVector] =
    for
      cipher <- getInstance[F](transformation, provider)
      _ <- cipher.certificateInitEncrypt[F](certificate, random)
      output <- cipher.doFinalF[F](input)
    yield output

  def certificateDecrypt[F[_]: Sync](transformation: CipherAlgorithm, certificate: Certificate,
                                     input: ByteVector, random: Option[SecureRandom] = None,
                                     provider: Option[Provider | JProvider] = None): F[ByteVector] =
    for
      cipher <- getInstance[F](transformation, provider)
      _ <- cipher.certificateInitDecrypt[F](certificate, random)
      output <- cipher.doFinalF[F](input)
    yield output
end CipherCompanion
