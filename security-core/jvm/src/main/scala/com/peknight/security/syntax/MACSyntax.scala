package com.peknight.security.syntax

import cats.effect.Sync
import com.peknight.security.spec.SecretKeySpec
import scodec.bits.ByteVector

import java.security.Key
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.Mac

trait MACSyntax:
  extension (mac: Mac)
    def initF[F[_]: Sync](key: Key, params: Option[AlgorithmParameterSpec] = None): F[Unit] =
      Sync[F].blocking(params.fold(mac.init(key))(mac.init(key, _)))
    def rawInitF[F[_]: Sync](key: ByteVector, params: Option[AlgorithmParameterSpec] = None): F[Unit] =
      val keySpec = SecretKeySpec(key, mac.getAlgorithm)
      Sync[F].blocking(params.fold(mac.init(keySpec))(mac.init(keySpec, _)))
    def updateF[F[_]: Sync](input: ByteVector): F[Unit] =
      Sync[F].blocking(mac.update(input.toArray))
    def doFinalF[F[_]: Sync]: F[ByteVector] =
      Sync[F].blocking(ByteVector(mac.doFinal()))
    def doFinalF[F[_]: Sync](input: ByteVector): F[ByteVector] =
      Sync[F].blocking(ByteVector(mac.doFinal(input.toArray)))
    def doFinalF[F[_]: Sync](input: Option[ByteVector]): F[ByteVector] =
      input.fold(doFinalF[F])(doFinalF[F])
    def getMacLengthF[F[_]: Sync]: F[Int] = Sync[F].blocking(mac.getMacLength)
  end extension
end MACSyntax
object MACSyntax extends MACSyntax
