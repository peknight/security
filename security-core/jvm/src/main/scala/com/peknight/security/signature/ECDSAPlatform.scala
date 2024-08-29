package com.peknight.security.signature

import cats.effect.Sync
import cats.syntax.applicative.*
import cats.syntax.applicativeError.*
import cats.syntax.either.*
import cats.syntax.functor.*
import com.peknight.error.Error
import com.peknight.error.syntax.either.asError
import com.peknight.scodec.bits.ext.syntax.byteVector.toUnsignedBigInt
import com.peknight.security.error.InvalidSignature
import com.peknight.security.provider.Provider
import com.peknight.security.signature.ECDSA.{convertConcatenatedToDER, convertDERToConcatenated}
import com.peknight.security.syntax.ecParameterSpec.signatureByteLength
import com.peknight.validation.std.either.{isTrue, typed}
import scodec.bits.ByteVector

import java.security.cert.Certificate
import java.security.interfaces.{ECPrivateKey, ECPublicKey}
import java.security.spec.AlgorithmParameterSpec
import java.security.{PrivateKey, PublicKey, SecureRandom, Provider as JProvider, Signature as JSignature}

trait ECDSAPlatform { self: ECDSA =>

  def signES[F[_]: Sync](privateKey: PrivateKey, data: ByteVector, params: Option[AlgorithmParameterSpec] = None,
                         random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[Either[Error, ByteVector]] =
    self.signature.sign[F](privateKey, data, params, random, provider).attempt
      .map { either =>
          for
            derEncodedBytes <- either.asError
            privateKey <- typed[ECPrivateKey](privateKey)
            res <- ECDSA.convertDERToConcatenated(derEncodedBytes, privateKey.getParams.signatureByteLength)
          yield res
      }

  def publicKeyVerifyES[F[_]: Sync](publicKey: PublicKey, data: ByteVector, signed: ByteVector,
                                    params: Option[AlgorithmParameterSpec] = None,
                                    provider: Option[Provider | JProvider] = None): F[Either[Error, Boolean]] =
    handleVerifyES[F](publicKey, signed)(signed =>
      self.signature.publicKeyVerify[F](publicKey, data, signed, params, provider)
    )

  def certificateVerifyES[F[_]: Sync](certificate: Certificate, data: ByteVector, signed: ByteVector,
                                      params: Option[AlgorithmParameterSpec] = None,
                                      provider: Option[Provider | JProvider] = None): F[Either[Error, Boolean]] =
    handleVerifyES[F](certificate.getPublicKey, signed)(signed =>
      self.signature.certificateVerify[F](certificate, data, signed, params, provider)
    )
    
  def handleVerifyES[F[_]: Sync](publicKey: PublicKey, signed: ByteVector)(f: ByteVector => F[Boolean]): F[Either[Error, Boolean]] =
    typed[ECPublicKey](publicKey) match
      case Right(publicKey) =>
        val params = publicKey.getParams
        if signed.length <= params.signatureByteLength then
          val rBytes = ECDSA.leftHalf(signed)
          val sBytes = ECDSA.rightHalf(signed)
          val r = rBytes.toUnsignedBigInt
          val s = sBytes.toUnsignedBigInt
          val orderN = BigInt(params.getOrder)
          if r.mod(orderN) != BigInt(0) && s.mod(orderN) != BigInt(0) then
            convertConcatenatedToDER(signed) match
              case Right(signed) => f(signed).attempt.map(_.asError)
              case Left(error) => error.asLeft.pure
          else false.asRight.pure
        else false.asRight.pure
      case Left(error) => error.asLeft.pure

  def publicKeyCheck[F[_]: Sync](publicKey: PublicKey, data: ByteVector, signed: ByteVector,
                                 params: Option[AlgorithmParameterSpec] = None,
                                 provider: Option[Provider | JProvider] = None): F[Either[Error, Unit]] =
    publicKeyVerifyES[F](publicKey, data, signed, params, provider).map(_.flatMap(isTrue(_, InvalidSignature)))

  def certificateCheck[F[_]: Sync](certificate: Certificate, data: ByteVector, signed: ByteVector,
                                   params: Option[AlgorithmParameterSpec] = None,
                                   provider: Option[Provider | JProvider] = None): F[Either[Error, Unit]] =
    certificateVerifyES[F](certificate, data, signed, params, provider).map(_.flatMap(isTrue(_, InvalidSignature)))

  override def getSignature[F[_]: Sync](provider: Option[Provider | JProvider] = None): F[JSignature] =
    self.signature.getSignature[F](provider)

  override def sign[F[_]: Sync](privateKey: PrivateKey, data: ByteVector, params: Option[AlgorithmParameterSpec] = None,
                                random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[ByteVector] =
    self.signature.sign[F](privateKey, data, params, random, provider)

  override def publicKeyVerify[F[_]: Sync](publicKey: PublicKey, data: ByteVector, signed: ByteVector,
                                           params: Option[AlgorithmParameterSpec] = None,
                                           provider: Option[Provider | JProvider] = None): F[Boolean] =
    self.signature.publicKeyVerify[F](publicKey, data, signed, params, provider)

  override def certificateVerify[F[_]: Sync](certificate: Certificate, data: ByteVector, signed: ByteVector,
                                             params: Option[AlgorithmParameterSpec] = None,
                                             provider: Option[Provider | JProvider] = None): F[Boolean] =
    self.signature.certificateVerify[F](certificate, data, signed, params, provider)
}
