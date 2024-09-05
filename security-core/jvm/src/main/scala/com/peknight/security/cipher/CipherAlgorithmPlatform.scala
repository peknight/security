package com.peknight.security.cipher

import cats.effect.Sync
import com.peknight.security.algorithm.Algorithm
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
                            aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                            provider: Option[Provider | JProvider] = None): F[ByteVector] =
    Cipher.keyCrypto[F](self, opmode, key, input, params, aad, random, provider)

  def keyEncrypt[F[_]: Sync](key: Key, input: ByteVector,
                             params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                             aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                             provider: Option[Provider | JProvider] = None): F[ByteVector] =
    Cipher.keyEncrypt[F](self, key, input, params, aad, random, provider)

  def keyDecrypt[F[_]: Sync](key: Key, input: ByteVector,
                             params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                             aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                             provider: Option[Provider | JProvider] = None): F[ByteVector] =
    Cipher.keyDecrypt[F](self, key, input, params, aad, random, provider)

  def keyWrap[F[_]: Sync](key: Key, keyToBeWrapped: Key,
                          params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                          aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                          provider: Option[Provider | JProvider] = None): F[ByteVector] =
    Cipher.keyWrap[F](self, key, keyToBeWrapped, params, aad, random, provider)

  def keyUnwrap[F[_]: Sync](key: Key, wrappedKey: ByteVector, wrappedKeyAlgorithm: Algorithm,
                            wrappedKeyType: WrappedKeyType,
                            params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                            aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                            provider: Option[Provider | JProvider] = None): F[Key] =
    Cipher.keyUnwrap[F](self, key, wrappedKey, wrappedKeyAlgorithm, wrappedKeyType, params, aad, random, provider)

  def certificateCrypto[F[_]: Sync](opmode: Opmode, certificate: Certificate, input: ByteVector,
                                    aad: Option[ByteVector] = None, random: Option[SecureRandom] = None,
                                    provider: Option[Provider | JProvider] = None): F[ByteVector] =
    Cipher.certificateCrypto[F](self, opmode, certificate, input, aad, random, provider)

  def certificateEncrypt[F[_]: Sync](certificate: Certificate, input: ByteVector, aad: Option[ByteVector] = None,
                                     random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    Cipher.certificateEncrypt[F](self, certificate, input, aad, random, provider)

  def certificateDecrypt[F[_]: Sync](certificate: Certificate, input: ByteVector, aad: Option[ByteVector] = None,
                                     random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    Cipher.certificateDecrypt[F](self, certificate, input, aad, random, provider)

  def certificateWrap[F[_]: Sync](certificate: Certificate, keyToBeWrapped: Key, aad: Option[ByteVector] = None,
                                  random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    Cipher.certificateWrap[F](self, certificate, keyToBeWrapped, aad, random, provider)

  def certificateUnwrap[F[_]: Sync](certificate: Certificate, wrappedKey: ByteVector, wrappedKeyAlgorithm: Algorithm,
                                    wrappedKeyType: WrappedKeyType, aad: Option[ByteVector] = None,
                                    random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[Key] =
    Cipher.certificateUnwrap[F](self, certificate, wrappedKey, wrappedKeyAlgorithm, wrappedKeyType, aad, random, provider)
}
