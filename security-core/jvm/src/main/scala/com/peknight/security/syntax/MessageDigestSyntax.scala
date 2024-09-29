package com.peknight.security.syntax

import cats.effect.Sync
import scodec.bits.ByteVector

import java.security.MessageDigest

trait MessageDigestSyntax:
  extension (messageDigest: MessageDigest)
    def getDigestLengthF[F[_]: Sync]: F[Int] = Sync[F].blocking(messageDigest.getDigestLength)
    def updateF[F[_]: Sync](input: ByteVector): F[Unit] = Sync[F].blocking(messageDigest.update(input.toArray))
    def digestF[F[_]: Sync]: F[ByteVector] = Sync[F].blocking(ByteVector(messageDigest.digest()))
    def digestF[F[_]: Sync](input: ByteVector): F[ByteVector] = 
      Sync[F].blocking(ByteVector(messageDigest.digest(input.toArray)))
    def digestF[F[_]: Sync](input: Option[ByteVector]): F[ByteVector] = input.fold(digestF[F])(digestF[F]) 
  end extension
end MessageDigestSyntax
object MessageDigestSyntax extends MessageDigestSyntax
