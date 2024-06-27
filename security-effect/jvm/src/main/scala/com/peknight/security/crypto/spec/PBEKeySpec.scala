package com.peknight.security.crypto.spec

import cats.effect.Sync
import scodec.bits.ByteVector

import javax.crypto.spec.PBEKeySpec as JPBEKeySpec

object PBEKeySpec:
  def apply[F[_]: Sync](password: String): F[JPBEKeySpec] = Sync[F].delay(JPBEKeySpec(password.toCharArray))
  def apply[F[_]: Sync](password: String, salt: ByteVector, iterationCount: Int, keyLength: Int): F[JPBEKeySpec] =
    Sync[F].delay(JPBEKeySpec(password.toCharArray, salt.toArray, iterationCount, keyLength))
  def apply[F[_]: Sync](password: String, salt: ByteVector, iterationCount: Int): F[JPBEKeySpec] =
    Sync[F].delay(JPBEKeySpec(password.toCharArray, salt.toArray, iterationCount))
end PBEKeySpec
