package com.peknight.security.crypto.spec

import cats.effect.Sync
import scodec.bits.ByteVector

import javax.crypto.spec.IvParameterSpec as JIvParameterSpec

object IvParameterSpec:
  def apply[F[_]: Sync](iv: ByteVector): F[JIvParameterSpec] = Sync[F].blocking(JIvParameterSpec(iv.toArray))
end IvParameterSpec
