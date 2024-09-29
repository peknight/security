package com.peknight.security.syntax

import cats.effect.Sync
import com.peknight.security.algorithm.Algorithm
import com.peknight.security.cipher.{Opmode, WrappedKeyType}
import com.peknight.security.cipher.Opmode.{Decrypt, Encrypt, Unwrap, Wrap}
import com.peknight.security.spec.{IvParameterSpec, SecretKeySpec, SecretKeySpecAlgorithm}
import scodec.bits.ByteVector

import java.security.cert.Certificate
import java.security.spec.AlgorithmParameterSpec
import java.security.{AlgorithmParameters, Key, SecureRandom}
import javax.crypto.Cipher

trait CipherSyntax:
  extension (cipher: Cipher)
    def keyInit[F[_]: Sync](opmode: Opmode, key: Key,
                            params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                            random: Option[SecureRandom] = None): F[Unit] =
      Sync[F].blocking {
        (params, random) match
          case (Some(params: AlgorithmParameterSpec), Some(random)) => cipher.init(opmode.mode, key, params, random)
          case (Some(params: AlgorithmParameters), Some(random)) => cipher.init(opmode.mode, key, params, random)
          case (Some(params: AlgorithmParameterSpec), None) => cipher.init(opmode.mode, key, params)
          case (Some(params: AlgorithmParameters), None) => cipher.init(opmode.mode, key, params)
          case (_, Some(random)) => cipher.init(opmode.mode, key, random)
          case (_, None) => cipher.init(opmode.mode, key)
      }

    def keyInitEncrypt[F[_]: Sync](key: Key, params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                                   random: Option[SecureRandom] = None): F[Unit] =
      keyInit[F](Encrypt, key, params, random)

    def keyInitDecrypt[F[_]: Sync](key: Key, params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                                   random: Option[SecureRandom] = None): F[Unit] =
      keyInit[F](Decrypt, key, params, random)

    def keyInitWrap[F[_] : Sync](key: Key, params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                                 random: Option[SecureRandom] = None): F[Unit] =
      keyInit[F](Wrap, key, params, random)

    def keyInitUnwrap[F[_] : Sync](key: Key, params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
                                   random: Option[SecureRandom] = None): F[Unit] =
      keyInit[F](Unwrap, key, params, random)

    def rawKeyInit[F[_]: Sync](algorithm: SecretKeySpecAlgorithm, opmode: Opmode, key: ByteVector,
                               iv: Option[ByteVector] = None, random: Option[SecureRandom] = None): F[Unit] =
      keyInit[F](opmode, SecretKeySpec(key, algorithm), iv.map(IvParameterSpec.apply), random)

    def rawKeyInitEncrypt[F[_]: Sync](algorithm: SecretKeySpecAlgorithm, key: ByteVector,
                                      iv: Option[ByteVector] = None, random: Option[SecureRandom] = None): F[Unit] =
      rawKeyInit[F](algorithm, Encrypt, key, iv, random)

    def rawKeyInitDecrypt[F[_]: Sync](algorithm: SecretKeySpecAlgorithm, key: ByteVector,
                                      iv: Option[ByteVector] = None, random: Option[SecureRandom] = None): F[Unit] =
      rawKeyInit[F](algorithm, Decrypt, key, iv, random)

    def rawKeyInitWrap[F[_] : Sync](algorithm: SecretKeySpecAlgorithm, key: ByteVector,
                                    iv: Option[ByteVector] = None, random: Option[SecureRandom] = None): F[Unit] =
      rawKeyInit[F](algorithm, Wrap, key, iv, random)

    def rawKeyInitUnwrap[F[_] : Sync](algorithm: SecretKeySpecAlgorithm, key: ByteVector,
                                      iv: Option[ByteVector] = None, random: Option[SecureRandom] = None): F[Unit] =
      rawKeyInit[F](algorithm, Unwrap, key, iv, random)

    def certificateInit[F[_]: Sync](opmode: Opmode, certificate: Certificate, random: Option[SecureRandom] = None)
    : F[Unit] =
      Sync[F].blocking {
        random match
          case Some(random) => cipher.init(opmode.mode, certificate, random)
          case None => cipher.init(opmode.mode, certificate)
      }

    def certificateInitEncrypt[F[_]: Sync](certificate: Certificate, random: Option[SecureRandom] = None): F[Unit] =
      certificateInit[F](Encrypt, certificate, random)

    def certificateInitDecrypt[F[_]: Sync](certificate: Certificate, random: Option[SecureRandom] = None): F[Unit] =
      certificateInit[F](Decrypt, certificate, random)

    def certificateInitWrap[F[_] : Sync](certificate: Certificate, random: Option[SecureRandom] = None): F[Unit] =
      certificateInit[F](Wrap, certificate, random)

    def certificateInitUnwrap[F[_] : Sync](certificate: Certificate, random: Option[SecureRandom] = None): F[Unit] =
      certificateInit[F](Unwrap, certificate, random)

    def updateAADF[F[_]: Sync](src: ByteVector): F[Unit] = Sync[F].blocking(cipher.updateAAD(src.toArray))

    def doFinalF[F[_]: Sync]: F[ByteVector] = Sync[F].blocking(ByteVector(cipher.doFinal()))
    def doFinalF[F[_]: Sync](input: ByteVector): F[ByteVector] = 
      Sync[F].blocking(ByteVector(cipher.doFinal(input.toArray)))
    def doFinalF[F[_]: Sync](input: Option[ByteVector] = None): F[ByteVector] = input.fold(doFinalF[F])(doFinalF[F])
    
    def wrapF[F[_]: Sync](key: Key): F[ByteVector] = Sync[F].blocking(ByteVector(cipher.wrap(key)))
    def unwrapF[F[_]: Sync](wrappedKey: ByteVector, wrappedKeyAlgorithm: Algorithm, wrappedKeyType: WrappedKeyType)
    : F[Key] =
      Sync[F].blocking(cipher.unwrap(wrappedKey.toArray, wrappedKeyAlgorithm.algorithm, wrappedKeyType.keyType))
  end extension
end CipherSyntax
object CipherSyntax extends CipherSyntax
