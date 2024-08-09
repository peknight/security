package com.peknight.security

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.provider.Provider
import com.peknight.security.signature.SignatureAlgorithm
import com.peknight.security.syntax.signature.*
import scodec.bits.ByteVector

import java.security.cert.Certificate
import java.security.{PrivateKey, PublicKey, SecureRandom, Provider as JProvider, Signature as JSignature}

object Signature:
  def getInstance[F[_]: Sync](algorithm: SignatureAlgorithm, provider: Option[Provider | JProvider] = None): F[JSignature] =
    Sync[F].blocking {
      provider match
        case Some(provider: Provider) => JSignature.getInstance(algorithm.algorithm, provider.name)
        case Some(provider: JProvider) => JSignature.getInstance(algorithm.algorithm, provider)
        case _ => JSignature.getInstance(algorithm.algorithm)
    }

  def sign[F[_]: Sync](algorithm: SignatureAlgorithm, privateKey: PrivateKey, data: ByteVector,
                       provider: Option[Provider | JProvider] = None, random: Option[SecureRandom] = None): F[ByteVector] =
    for
      signature <- getInstance[F](algorithm, provider)
      _ <- signature.initSignF[F](privateKey, random)
      _ <- signature.updateF[F](data)
      res <- signature.signF[F]
    yield res

  def publicKeyVerify[F[_]: Sync](algorithm: SignatureAlgorithm, publicKey: PublicKey, data: ByteVector,
                                  signed: ByteVector, provider: Option[Provider | JProvider] = None): F[Boolean] =
    for
      signature <- getInstance[F](algorithm, provider)
      _ <- signature.initVerifyF[F](publicKey)
      _ <- signature.updateF[F](data)
      res <- signature.verifyF[F](signed)
    yield res

  def certificateVerify[F[_]: Sync](algorithm: SignatureAlgorithm, certificate: Certificate, data: ByteVector,
                                    signed: ByteVector, provider: Option[Provider | JProvider] = None): F[Boolean] =
    for
      signature <- getInstance[F](algorithm, provider)
      _ <- signature.initVerifyF[F](certificate)
      _ <- signature.updateF[F](data)
      res <- signature.verifyF[F](signed)
    yield res
end Signature
