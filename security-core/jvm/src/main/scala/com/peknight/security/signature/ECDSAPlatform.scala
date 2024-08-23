package com.peknight.security.signature

import cats.effect.Sync
import cats.syntax.applicativeError.*
import cats.syntax.functor.*
import com.peknight.error.Error
import com.peknight.error.syntax.either.asError
import com.peknight.security.provider.Provider
import com.peknight.security.signature.ECDSA.convertDERToConcatenated
import com.peknight.security.syntax.ecParameterSpec.signatureByteLength
import com.peknight.validation.std.either.typed
import scodec.bits.ByteVector

import java.security.cert.Certificate
import java.security.interfaces.ECPrivateKey
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

  // def handleVerifyES[F[_]: Sync](): F[Either[SecurityError, Boolean]] =

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
