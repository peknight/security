package com.peknight.security.syntax

import cats.effect.Sync
import com.peknight.security.cipher.{Cipher, Opmode}
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.security.cert.Certificate
import java.security.spec.AlgorithmParameterSpec
import java.security.{AlgorithmParameters, Key, SecureRandom, Provider as JProvider}

trait CertificateSyntax:
  extension (certificate: Certificate)
    def crypto[F[_]: Sync](opmode: Opmode, input: ByteVector, random: Option[SecureRandom] = None,
                           provider: Option[Provider | JProvider] = None): F[ByteVector] =
      Cipher.certificateAlgorithmCrypto[F](opmode, certificate, input, random, provider)

    def encrypt[F[_] : Sync](input: ByteVector, random: Option[SecureRandom] = None,
                             provider: Option[Provider | JProvider] = None): F[ByteVector] =
      Cipher.certificateAlgorithmEncrypt[F](certificate, input, random, provider)

    def decrypt[F[_] : Sync](input: ByteVector, random: Option[SecureRandom] = None,
                             provider: Option[Provider | JProvider] = None): F[ByteVector] =
      Cipher.certificateAlgorithmDecrypt[F](certificate, input, random, provider)
  end extension
end CertificateSyntax
object CertificateSyntax extends CertificateSyntax