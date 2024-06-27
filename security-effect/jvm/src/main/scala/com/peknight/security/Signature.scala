package com.peknight.security

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.signature.SignatureAlgorithm
import com.peknight.security.syntax.signature.*
import scodec.bits.ByteVector

import java.security.{PrivateKey, PublicKey, Signature as JSignature}

object Signature:
  def getInstance[F[_]: Sync](algorithm: SignatureAlgorithm): F[JSignature] =
    Sync[F].blocking(JSignature.getInstance(algorithm.algorithm))

  def sign[F[_]: Sync](algorithm: SignatureAlgorithm, privateKey: PrivateKey)(data: ByteVector): F[ByteVector] =
    for
      signature <- getInstance[F](algorithm)
      _ <- signature.initSignF[F](privateKey)
      _ <- signature.updateF[F](data)
      res <- signature.signF[F]
    yield res

  def verify[F[_]: Sync](algorithm: SignatureAlgorithm, publicKey: PublicKey)(data: ByteVector, signed: ByteVector): F[Boolean] =
    for
      signature <- getInstance[F](algorithm)
      _ <- signature.initVerifyF[F](publicKey)
      _ <- signature.updateF[F](data)
      res <- signature.verifyF[F](signed)
    yield res
end Signature
