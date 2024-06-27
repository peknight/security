package com.peknight.security.crypto.spec

import cats.effect.Sync
import scodec.bits.ByteVector

import java.security.spec.AlgorithmParameterSpec
import javax.crypto.spec.PBEParameterSpec as JPBEParameterSpec

object PBEParameterSpec:
  def apply[F[_]: Sync](salt: ByteVector, iterationCount: Int): F[JPBEParameterSpec] =
    Sync[F].delay(JPBEParameterSpec(salt.toArray, iterationCount))
  def apply[F[_]: Sync](salt: ByteVector, iterationCount: Int, paramSpec: AlgorithmParameterSpec): F[JPBEParameterSpec] =
    Sync[F].delay(JPBEParameterSpec(salt.toArray, iterationCount, paramSpec))
end PBEParameterSpec
