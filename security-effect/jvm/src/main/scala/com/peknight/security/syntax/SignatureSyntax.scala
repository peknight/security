package com.peknight.security.syntax

import cats.effect.Sync
import scodec.bits.ByteVector

import java.security.{PrivateKey, PublicKey, Signature}

trait SignatureSyntax:
  extension (signature: Signature)
    def updateF[F[_]: Sync](data: ByteVector): F[Unit] =
      Sync[F].blocking(signature.update(data.toArray))
    def initSignF[F[_]: Sync](privateKey: PrivateKey): F[Unit] =
      Sync[F].blocking(signature.initSign(privateKey))
    def signF[F[_]: Sync]: F[ByteVector] =
      Sync[F].blocking(ByteVector(signature.sign()))
    def initVerifyF[F[_]: Sync](publicKey: PublicKey): F[Unit] =
      Sync[F].blocking(signature.initVerify(publicKey))
    def verifyF[F[_]: Sync](signed: ByteVector): F[Boolean] =
      Sync[F].blocking(signature.verify(signed.toArray))
  end extension
end SignatureSyntax
object SignatureSyntax extends SignatureSyntax
