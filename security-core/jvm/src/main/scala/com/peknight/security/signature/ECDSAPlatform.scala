package com.peknight.security.signature

import cats.effect.Sync
import cats.syntax.applicative.*
import cats.syntax.applicativeError.*
import cats.syntax.either.*
import cats.syntax.functor.*
import com.peknight.error.Error
import com.peknight.error.syntax.either.{asError, label}
import com.peknight.scodec.bits.ext.syntax.byteVector.toUnsignedBigInt
import com.peknight.security.error.{InvalidECDSASignatureFormat, InvalidSignature}
import com.peknight.security.provider.Provider
import com.peknight.security.signature.ECDSA.{convertConcatenatedToDER, convertDERToConcatenated}
import com.peknight.security.syntax.ecParameterSpec.signatureByteLength
import com.peknight.validation.spire.math.interval.either.atOrBelow
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
                                    provider: Option[Provider | JProvider] = None): F[Either[Error, Unit]] =
    handleVerifyES[F](publicKey, signed)(signed =>
      self.signature.publicKeyVerify[F](publicKey, data, signed, params, provider)
    )

  def certificateVerifyES[F[_]: Sync](certificate: Certificate, data: ByteVector, signed: ByteVector,
                                      params: Option[AlgorithmParameterSpec] = None,
                                      provider: Option[Provider | JProvider] = None): F[Either[Error, Unit]] =
    handleVerifyES[F](certificate.getPublicKey, signed)(signed =>
      self.signature.certificateVerify[F](certificate, data, signed, params, provider)
    )
    
  def handleVerifyES[F[_]: Sync](publicKey: PublicKey, signed: ByteVector)(f: ByteVector => F[Boolean]): F[Either[Error, Unit]] =
    val either = 
      for
        publicKey <- typed[ECPublicKey](publicKey)
        params = publicKey.getParams
        _ <- atOrBelow(signed.length, params.signatureByteLength.toLong).label("signature length")
        rBytes = ECDSA.leftHalf(signed)
        sBytes = ECDSA.rightHalf(signed)
        r = rBytes.toUnsignedBigInt
        s = sBytes.toUnsignedBigInt
        orderN = BigInt(params.getOrder)
        _ <- isTrue(r.mod(orderN) != BigInt(0) && s.mod(orderN) != BigInt(0), InvalidSignature)
        signed <- convertConcatenatedToDER(signed)
      yield
        f(signed).attempt.map { either =>
          for
            verified <- either.asError
            _ <- isTrue(verified, InvalidSignature)
          yield ()
        }
    either.fold(_.asLeft.pure[F], identity)    

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
