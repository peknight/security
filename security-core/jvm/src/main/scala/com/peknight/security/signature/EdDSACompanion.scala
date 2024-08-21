package com.peknight.security.signature

import cats.effect.Sync
import cats.syntax.either.*
import cats.syntax.functor.*
import com.peknight.security.error.{SecurityError, UnknownParameterSpecName}
import com.peknight.security.key.factory.KeyFactory
import com.peknight.security.provider.Provider
import com.peknight.security.signature.EdDSA.{xCoordinateOdd, yCoordinate}
import com.peknight.security.spec.{EdECPoint, EdECPrivateKeySpec, NamedParameterSpec}
import scodec.bits.ByteVector

import java.security.Provider as JProvider
import java.security.interfaces.{EdECPrivateKey, EdECPublicKey}
import java.security.spec.{EdECPublicKeySpec, EdECPoint as JEdECPoint, EdECPrivateKeySpec as JEdECPrivateKeySpec}

trait EdDSACompanion:
  def point(publicKeyBytes: ByteVector): JEdECPoint =
    EdECPoint(xCoordinateOdd(publicKeyBytes), yCoordinate(publicKeyBytes))

  def publicKeySpec(edDSA: EdDSA, publicKeyBytes: ByteVector): EdECPublicKeySpec =
    new EdECPublicKeySpec(NamedParameterSpec(edDSA), point(publicKeyBytes))

  def privateKeySpec(edDSA: EdDSA, privateKeyBytes: ByteVector): JEdECPrivateKeySpec =
    EdECPrivateKeySpec(NamedParameterSpec(edDSA), privateKeyBytes)

  def publicKey[F[_]: Sync](edDSA: EdDSA, publicKeyBytes: ByteVector, provider: Option[Provider | JProvider])
  : F[EdECPublicKey] =
    KeyFactory.generatePublic[F](EdDSA, publicKeySpec(edDSA, publicKeyBytes), provider).map(_.asInstanceOf[EdECPublicKey])

  def privateKey[F[_]: Sync](edDSA: EdDSA, privateKeyBytes: ByteVector, provider: Option[Provider | JProvider])
  : F[EdECPrivateKey] =
    KeyFactory.generatePrivate[F](EdDSA, privateKeySpec(edDSA, privateKeyBytes), provider).map(_.asInstanceOf[EdECPrivateKey])

  def getParameterSpecName(publicKey: EdECPublicKey): Either[SecurityError, EdDSA] =
    publicKey.getParams.getName match
      case Ed25519.algorithm => Ed25519.asRight
      case Ed448.algorithm => Ed448.asRight
      case algorithm => UnknownParameterSpecName(algorithm).asLeft

  // def rawPublicKey(publicKey: EdECPublicKey): Either[SecurityError, ByteVector] =
  //   getParameterSpecName(publicKey).map { edDSA =>
  //     val edECPoint = publicKey.getPoint
  //     val yReversedBytes = adjustByteVectorLength(ByteVector(edECPoint.getY.toByteArray).reverse, byteLength)
  //     val byteToOrWith = if edECPoint.isXOdd then -128.toByte else 0.toByte
  //     yReversedBytes.lastOption.fold(yReversedBytes)(last => yReversedBytes.init :+ (last | byteToOrWith).toByte)
  //   }
end EdDSACompanion
