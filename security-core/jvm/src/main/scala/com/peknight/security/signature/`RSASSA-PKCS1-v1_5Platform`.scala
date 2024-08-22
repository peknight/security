package com.peknight.security.signature

import cats.effect.Sync
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.security.cert.Certificate
import java.security.spec.AlgorithmParameterSpec
import java.security.{PrivateKey, PublicKey, SecureRandom, Provider as JProvider, Signature as JSignature}

trait `RSASSA-PKCS1-v1_5Platform` { self: `RSASSA-PKCS1-v1_5` =>

  def getSignature[F[_]: Sync](provider: Option[Provider | JProvider] = None): F[JSignature] =
    Signature.getInstance[F](self.signature, provider)

  def sign[F[_]: Sync](privateKey: PrivateKey, data: ByteVector, params: Option[AlgorithmParameterSpec] = None,
                       random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None): F[ByteVector] =
    Signature.sign[F](self.signature, privateKey, data, params, random, provider)

  def publicKeyVerify[F[_]: Sync](publicKey: PublicKey, data: ByteVector, signed: ByteVector,
                                  params: Option[AlgorithmParameterSpec] = None,
                                  provider: Option[Provider | JProvider] = None): F[Boolean] =
    Signature.publicKeyVerify[F](self.signature, publicKey, data, signed, params, provider)

  def certificateVerify[F[_]: Sync](certificate: Certificate, data: ByteVector, signed: ByteVector,
                                    params: Option[AlgorithmParameterSpec] = None,
                                    provider: Option[Provider | JProvider] = None): F[Boolean] =
    Signature.certificateVerify[F](self.signature, certificate, data, signed, params, provider)
}
