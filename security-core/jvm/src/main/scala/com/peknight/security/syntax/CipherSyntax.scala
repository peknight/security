package com.peknight.security.syntax

import cats.effect.Sync
import com.peknight.security.cipher.Opmode
import com.peknight.security.cipher.Opmode.{Decrypt, Encrypt}
import com.peknight.security.spec.{IvParameterSpec, SecretKeySpec, SecretKeySpecAlgorithm}
import scodec.bits.ByteVector

import java.security.cert.Certificate
import java.security.spec.AlgorithmParameterSpec
import java.security.{AlgorithmParameters, Key, SecureRandom}
import javax.crypto.Cipher

trait CipherSyntax:
  extension (cipher: Cipher)
    def keyInit[F[_]: Sync](opmode: Opmode, key: Key, params: Option[AlgorithmParameterSpec | AlgorithmParameters] = None,
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

    def rawKeyInit[F[_]: Sync](algorithm: SecretKeySpecAlgorithm, opmode: Opmode, key: ByteVector,
                               iv: Option[ByteVector] = None, random: Option[SecureRandom] = None): F[Unit] =
      keyInit[F](opmode, SecretKeySpec(key, algorithm), iv.map(IvParameterSpec.apply), random)

    def rawKeyInitEncrypt[F[_]: Sync](algorithm: SecretKeySpecAlgorithm, key: ByteVector,
                                      iv: Option[ByteVector] = None, random: Option[SecureRandom] = None): F[Unit] =
      rawKeyInit[F](algorithm, Encrypt, key, iv, random)

    def rawKeyInitDecrypt[F[_]: Sync](algorithm: SecretKeySpecAlgorithm, key: ByteVector,
                                      iv: Option[ByteVector] = None, random: Option[SecureRandom] = None): F[Unit] =
      rawKeyInit[F](algorithm, Decrypt, key, iv, random)

    def certificateInit[F[_]: Sync](opmode: Opmode, certificate: Certificate, random: Option[SecureRandom] = None): F[Unit] =
      Sync[F].blocking {
        random match
          case Some(random) => cipher.init(opmode.mode, certificate, random)
          case None => cipher.init(opmode.mode, certificate)
      }

    def certificateInitEncrypt[F[_]: Sync](certificate: Certificate, random: Option[SecureRandom] = None): F[Unit] =
      certificateInit[F](Encrypt, certificate, random)

    def certificateInitDecrypt[F[_]: Sync](certificate: Certificate, random: Option[SecureRandom] = None): F[Unit] =
      certificateInit[F](Decrypt, certificate, random)

    def updateAADF[F[_]: Sync](src: ByteVector): F[Unit] = Sync[F].blocking(cipher.updateAAD(src.toArray))

    def doFinalF[F[_]: Sync]: F[ByteVector] = Sync[F].blocking(ByteVector(cipher.doFinal()))
    def doFinalF[F[_]: Sync](input: ByteVector): F[ByteVector] = Sync[F].blocking(ByteVector(cipher.doFinal(input.toArray)))
    def doFinalF[F[_]: Sync](input: Option[ByteVector] = None): F[ByteVector] = input.fold(doFinalF[F])(doFinalF[F])
  end extension
end CipherSyntax
object CipherSyntax extends CipherSyntax
