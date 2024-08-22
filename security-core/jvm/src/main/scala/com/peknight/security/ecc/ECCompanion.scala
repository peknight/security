package com.peknight.security.ecc

import cats.effect.Sync
import cats.syntax.either.*
import cats.syntax.functor.*
import com.peknight.scodec.bits.ext.syntax.bigInt.toUnsignedBytes
import com.peknight.security.error.{PointNotOnCurve, SecurityError, UnsupportedECField}
import com.peknight.security.provider.Provider
import com.peknight.security.spec.{ECPoint, ECPrivateKeySpec}
import scodec.bits.ByteVector

import java.security.Provider as JProvider
import java.security.interfaces.{ECKey, ECPrivateKey, ECPublicKey}
import java.security.spec.{ECFieldFp, ECParameterSpec, ECPublicKeySpec, ECPrivateKeySpec as JECPrivateKeySpec}
import scala.reflect.ClassTag

trait ECCompanion:
  def publicKeySpec(x: BigInt, y: BigInt, params: ECParameterSpec): ECPublicKeySpec =
    new ECPublicKeySpec(ECPoint(x, y), params)

  def privateKeySpec(s: BigInt, params: ECParameterSpec): JECPrivateKeySpec = ECPrivateKeySpec(s, params)

  def publicKey[F[_]: Sync](x: BigInt, y: BigInt, params: ECParameterSpec, provider: Option[Provider | JProvider] = None)
  : F[ECPublicKey] =
    EC.generatePublic[F](publicKeySpec(x, y, params), provider).map(_.asInstanceOf[ECPublicKey])

  def privateKey[F[_]: Sync](s: BigInt, params: ECParameterSpec, provider: Option[Provider | JProvider] = None)
  : F[ECPrivateKey] =
    EC.generatePrivate[F](privateKeySpec(s, params), provider).map(_.asInstanceOf[ECPrivateKey])

  def minByteLength(key: ECKey): Int = (key.getParams.getCurve.getField.getFieldSize + 7) / 8

  def rawXCoordinate(publicKey: ECPublicKey): ByteVector =
    BigInt(publicKey.getW.getAffineX).toUnsignedBytes(minByteLength(publicKey))
  def rawYCoordinate(publicKey: ECPublicKey): ByteVector =
    BigInt(publicKey.getW.getAffineY).toUnsignedBytes(minByteLength(publicKey))
  def rawPoint(publicKey: ECPublicKey): (ByteVector, ByteVector) = (rawXCoordinate(publicKey), rawYCoordinate(publicKey))

  def rawPrivateKey(privateKey: ECPrivateKey): ByteVector = BigInt(privateKey.getS).toUnsignedBytes(minByteLength(privateKey))

  def isPointOnCurve(x: BigInt, y: BigInt, params: ECParameterSpec): Either[SecurityError, Boolean] =
    val curve = params.getCurve
    curve.getField match
      case fp: ECFieldFp =>
        val a = BigInt(curve.getA)
        val b = BigInt(curve.getB)
        val p = BigInt(fp.getP)
        val leftSide = y.pow(2).mod(p)
        val rightSide = (x.pow(3) + (a * x) + b).mod(p)
        (leftSide == rightSide).asRight
      case field => UnsupportedECField(using ClassTag(field.getClass)).asLeft

  def checkPointOnCurve(x: BigInt, y: BigInt, params: ECParameterSpec): Either[SecurityError, Unit] =
    isPointOnCurve(x, y, params).flatMap {
      case true => ().asRight
      case false => PointNotOnCurve(x, y, params).asLeft
    }
end ECCompanion
