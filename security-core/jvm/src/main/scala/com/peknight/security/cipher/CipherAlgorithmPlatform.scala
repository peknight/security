package com.peknight.security.cipher

import cats.effect.Sync
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.security.cert.Certificate
import java.security.spec.AlgorithmParameterSpec
import java.security.{AlgorithmParameters, Key, SecureRandom, Provider as JProvider}
import javax.crypto.Cipher as JCipher

trait CipherAlgorithmPlatform { self: CipherAlgorithm =>

  def getCipher[F[_]: Sync](provider: Option[Provider | JProvider] = None): F[JCipher] =
    Cipher.getInstance[F](self, provider)

  def getMaxAllowedKeyLength[F[_]: Sync]: F[Int] = Cipher.getMaxAllowedKeyLength[F](self)

  def keyCrypto[F[_]: Sync](opmode: Opmode, key: Key, input: ByteVector,
                            params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                            random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    Cipher.keyCrypto[F](self, opmode, key, input, params, random, provider)

  def keyEncrypt[F[_]: Sync](key: Key, input: ByteVector,
                             params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                             random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    Cipher.keyEncrypt[F](self, key, input, params, random, provider)

  def keyDecrypt[F[_]: Sync](key: Key, input: ByteVector,
                             params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                             random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    Cipher.keyDecrypt[F](self, key, input, params, random, provider)


  def certificateCrypto[F[_]: Sync](opmode: Opmode, certificate: Certificate, input: ByteVector,
                                    random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    Cipher.certificateCrypto[F](self, opmode, certificate, input, random, provider)

  def certificateEncrypt[F[_]: Sync](certificate: Certificate, input: ByteVector,
                                     random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    Cipher.certificateEncrypt[F](self, certificate, input, random, provider)

  def certificateDecrypt[F[_]: Sync](certificate: Certificate, input: ByteVector,
                                     random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    Cipher.certificateDecrypt[F](self, certificate, input, random, provider)
}
