package com.peknight.security.crypto.syntax

import cats.effect.Sync

import java.security.Key
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.Mac
import scodec.bits.ByteVector

trait MacSyntax:
  extension (mac: Mac)
    def initF[F[_]: Sync](key: Key, params: Option[AlgorithmParameterSpec] = None): F[Unit] =
      Sync[F].blocking(params.fold(mac.init(key))(mac.init(key, _)))
    def updateF[F[_]: Sync](input: ByteVector): F[Unit] =
      Sync[F].blocking(mac.update(input.toArray))
    def doFinalF[F[_]: Sync]: F[ByteVector] =
      Sync[F].blocking(ByteVector(mac.doFinal()))
    def doFinalF[F[_]: Sync](input: ByteVector): F[ByteVector] =
      Sync[F].blocking(ByteVector(mac.doFinal(input.toArray)))
    def doFinalF[F[_]: Sync](input: Option[ByteVector]): F[ByteVector] =
      input.fold(doFinalF[F])(doFinalF[F])
  end extension
end MacSyntax
object MacSyntax extends MacSyntax
