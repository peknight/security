package com.peknight.security.cipher

import cats.effect.Sync
import cats.syntax.applicative.*
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
    getInstanceRaw[F](transformation.transformation, provider)

  private def getInstanceRaw[F[_]: Sync](transformation: String, provider: Option[Provider | JProvider] = None): F[JCipher] =
    Sync[F].blocking {
      provider match
        case Some(provider: Provider) => JCipher.getInstance(transformation, provider.name)
        case Some(provider: JProvider) => JCipher.getInstance(transformation, provider)
        case _ => JCipher.getInstance(transformation)
    }

  def getMaxAllowedKeyLength[F[_]: Sync](transformation: CipherAlgorithm): F[Int] =
    Sync[F].blocking(JCipher.getMaxAllowedKeyLength(transformation.transformation))

  private def handleCrypto[F[_]: Sync](transformation: CipherAlgorithm, input: ByteVector,
                                       aad: Option[ByteVector] = None, provider: Option[Provider | JProvider] = None)
                                      (init: JCipher => F[Unit]): F[ByteVector] =
    handleCryptoRaw[F](transformation.transformation, input, aad, provider)(init)

  private def handleCryptoRaw[F[_]: Sync](transformation: String, input: ByteVector,
                                          aad: Option[ByteVector] = None, provider: Option[Provider | JProvider] = None)
                                         (init: JCipher => F[Unit]): F[ByteVector] =
    for
      cipher <- getInstanceRaw[F](transformation, provider)
      _ <- init(cipher)
      _ <- aad.filter(_.nonEmpty).fold(().pure[F])(cipher.updateAADF[F])
      output <- cipher.doFinalF[F](input)
    yield
      output

  def keyCrypto[F[_]: Sync](transformation: CipherAlgorithm, opmode: Opmode, key: Key, input: ByteVector,
                            params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                            aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                            provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.keyInit[F](opmode, key, params, random))

  def keyEncrypt[F[_]: Sync](transformation: CipherAlgorithm, key: Key, input: ByteVector,
                             params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                             aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                             provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.keyInitEncrypt[F](key, params, random))

  def keyDecrypt[F[_]: Sync](transformation: CipherAlgorithm, key: Key, input: ByteVector,
                             params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                             aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                             provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.keyInitDecrypt[F](key, params, random))

  def keyWrap[F[_] : Sync](transformation: CipherAlgorithm, key: Key, input: ByteVector,
                           params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                           aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                           provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.keyInitWrap[F](key, params, random))

  def keyUnwrap[F[_] : Sync](transformation: CipherAlgorithm, key: Key, input: ByteVector,
                             params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                             aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                             provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.keyInitUnwrap[F](key, params, random))

  def keyAlgorithmCrypto[F[_]: Sync](opmode: Opmode, key: Key, input: ByteVector,
                                     params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                                     aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                                     provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCryptoRaw[F](key.getAlgorithm, input, aad, provider)(_.keyInit[F](opmode, key, params, random))

  def keyAlgorithmEncrypt[F[_]: Sync](key: Key, input: ByteVector,
                                      params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                                      aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                                      provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCryptoRaw[F](key.getAlgorithm, input, aad, provider)(_.keyInitEncrypt[F](key, params, random))

  def keyAlgorithmDecrypt[F[_]: Sync](key: Key, input: ByteVector,
                                      params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                                      aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                                      provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCryptoRaw[F](key.getAlgorithm, input, aad, provider)(_.keyInitDecrypt[F](key, params, random))

  def keyAlgorithmWrap[F[_] : Sync](key: Key, input: ByteVector,
                                    params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                                    aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                                    provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCryptoRaw[F](key.getAlgorithm, input, aad, provider)(_.keyInitWrap[F](key, params, random))

  def keyAlgorithmUnwrap[F[_] : Sync](key: Key, input: ByteVector,
                                      params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                                      aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                                      provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCryptoRaw[F](key.getAlgorithm, input, aad, provider)(_.keyInitUnwrap[F](key, params, random))

  def rawKeyCrypto[F[_]: Sync](transformation: CipherAlgorithm & SecretKeyFactoryAlgorithm, opmode: Opmode,
                               key: ByteVector, input: ByteVector, iv: Option[ByteVector] = None,
                               aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                               provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.rawKeyInit[F](transformation, opmode, key, iv, random))

  def rawKeyEncrypt[F[_]: Sync](transformation: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector,
                                input: ByteVector, iv: Option[ByteVector] = None, aad: Option[ByteVector] = None,
                                random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.rawKeyInitEncrypt[F](transformation, key, iv, random))

  def rawKeyDecrypt[F[_]: Sync](transformation: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector,
                                input: ByteVector, iv: Option[ByteVector] = None, aad: Option[ByteVector] = None,
                                random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.rawKeyInitDecrypt[F](transformation, key, iv, random))

  def rawKeyWrap[F[_] : Sync](transformation: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector,
                              input: ByteVector, iv: Option[ByteVector] = None, aad: Option[ByteVector] = None,
                              random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.rawKeyInitWrap[F](transformation, key, iv, random))

  def rawKeyUnwrap[F[_] : Sync](transformation: CipherAlgorithm & SecretKeyFactoryAlgorithm, key: ByteVector,
                                input: ByteVector, iv: Option[ByteVector] = None, aad: Option[ByteVector] = None,
                                random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.rawKeyInitUnwrap[F](transformation, key, iv, random))

  def certificateCrypto[F[_]: Sync](transformation: CipherAlgorithm, opmode: Opmode, certificate: Certificate,
                                    input: ByteVector, aad: Option[ByteVector] = None,
                                    random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.certificateInit[F](opmode, certificate, random))

  def certificateEncrypt[F[_]: Sync](transformation: CipherAlgorithm, certificate: Certificate,
                                     input: ByteVector, aad: Option[ByteVector] = None,
                                     random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.certificateInitEncrypt[F](certificate, random))

  def certificateDecrypt[F[_]: Sync](transformation: CipherAlgorithm, certificate: Certificate,
                                     input: ByteVector, aad: Option[ByteVector] = None,
                                     random: Option[SecureRandom] = None,
                                     provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.certificateInitDecrypt[F](certificate, random))

  def certificateWrap[F[_] : Sync](transformation: CipherAlgorithm, certificate: Certificate,
                                   input: ByteVector, aad: Option[ByteVector] = None,
                                   random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.certificateInitWrap[F](certificate, random))

  def certificateUnwrap[F[_] : Sync](transformation: CipherAlgorithm, certificate: Certificate,
                                     input: ByteVector, aad: Option[ByteVector] = None,
                                     random: Option[SecureRandom] = None,
                                     provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCrypto[F](transformation, input, aad, provider)(_.certificateInitUnwrap[F](certificate, random))

  def certificateAlgorithmCrypto[F[_]: Sync](opmode: Opmode, certificate: Certificate, input: ByteVector,
                                             aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                                             provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCryptoRaw[F](certificate.getPublicKey.getAlgorithm, input, aad, provider)(
      _.certificateInit[F](opmode, certificate, random)
    )

  def certificateAlgorithmEncrypt[F[_]: Sync](certificate: Certificate, input: ByteVector,
                                              aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                                              provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCryptoRaw[F](certificate.getPublicKey.getAlgorithm, input, aad, provider)(
      _.certificateInitEncrypt[F](certificate, random)
    )

  def certificateAlgorithmDecrypt[F[_]: Sync](certificate: Certificate, input: ByteVector,
                                              aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                                              provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCryptoRaw[F](certificate.getPublicKey.getAlgorithm, input, aad, provider)(
      _.certificateInitDecrypt[F](certificate, random)
    )

  def certificateAlgorithmWrap[F[_]: Sync](certificate: Certificate, input: ByteVector,
                                           aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                                           provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCryptoRaw[F](certificate.getPublicKey.getAlgorithm, input, aad, provider)(
      _.certificateInitWrap[F](certificate, random)
    )

  def certificateAlgorithmUnwrap[F[_]: Sync](certificate: Certificate, input: ByteVector,
                                             aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                                             provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleCryptoRaw[F](certificate.getPublicKey.getAlgorithm, input, aad, provider)(
      _.certificateInitUnwrap[F](certificate, random)
    )
end CipherCompanion
