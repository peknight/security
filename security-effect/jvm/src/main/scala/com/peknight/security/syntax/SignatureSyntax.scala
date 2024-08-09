package com.peknight.security.syntax

import cats.effect.Sync
import scodec.bits.ByteVector

import java.security.cert.Certificate
import java.security.{PrivateKey, PublicKey, SecureRandom, Signature}

trait SignatureSyntax:
  extension (signature: Signature)
    def updateF[F[_]: Sync](data: ByteVector): F[Unit] =
      Sync[F].blocking(signature.update(data.toArray))
    def initSignF[F[_]: Sync](privateKey: PrivateKey, random: Option[SecureRandom] = None): F[Unit] =
      Sync[F].blocking(random.fold(signature.initSign(privateKey))(signature.initSign(privateKey, _)))
    def signF[F[_]: Sync]: F[ByteVector] =
      Sync[F].blocking(ByteVector(signature.sign()))
    def initVerifyF[F[_]: Sync](publicKey: PublicKey): F[Unit] =
      Sync[F].blocking(signature.initVerify(publicKey))
    def initVerifyF[F[_]: Sync](certificate: Certificate): F[Unit] =
      Sync[F].blocking(signature.initVerify(certificate))
    def verifyF[F[_]: Sync](signed: ByteVector): F[Boolean] =
      Sync[F].blocking(signature.verify(signed.toArray))
  end extension
end SignatureSyntax
object SignatureSyntax extends SignatureSyntax
