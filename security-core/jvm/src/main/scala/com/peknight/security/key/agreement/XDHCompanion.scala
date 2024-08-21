package com.peknight.security.key.agreement

import cats.effect.Sync
import cats.syntax.either.*
import cats.syntax.functor.*
import com.peknight.scodec.bits.ext.syntax.bigInt.toByteVector
import com.peknight.scodec.bits.ext.syntax.byteVector.{adjustLength, toUnsignedBigInt}
import com.peknight.security.error.{SecurityError, UncheckedParameterSpec, UnknownParameterSpecName}
import com.peknight.security.key.factory.KeyFactory
import com.peknight.security.provider.Provider
import com.peknight.security.spec.{NamedParameterSpec, XECPrivateKeySpec, XECPublicKeySpec}
import scodec.bits.ByteVector

import java.security.Provider as JProvider
import java.security.interfaces.{XECPrivateKey, XECPublicKey}
import java.security.spec.{NamedParameterSpec as JNamedParameterSpec, XECPrivateKeySpec as JXECPrivateKeySpec, XECPublicKeySpec as JXECPublicKeySpec}
import scala.jdk.OptionConverters.*

trait XDHCompanion:

  def publicKeySpec(xdh: XDH, publicKeyBytes: ByteVector): JXECPublicKeySpec =
    val reversedBytes = publicKeyBytes.reverse
    val numBitsMod8 = xdh.bits % 8
    XECPublicKeySpec(
      NamedParameterSpec(xdh),
      reversedBytes.headOption.filter(_ => numBitsMod8 != 0).fold(reversedBytes)(
        head => (head & ((1 << numBitsMod8) - 1)).toByte +: reversedBytes.tail
      ).toUnsignedBigInt
    )

  def privateKeySpec(xdh: XDH, privateKeyBytes: ByteVector): JXECPrivateKeySpec =
    XECPrivateKeySpec(NamedParameterSpec(xdh), privateKeyBytes)


  def publicKey[F[_]: Sync](xdh: XDH, publicKeyBytes: ByteVector, provider: Option[Provider | JProvider])
  : F[XECPublicKey] =
    KeyFactory.generatePublic[F](XDH, publicKeySpec(xdh, publicKeyBytes), provider).map(_.asInstanceOf[XECPublicKey])

  def privateKey[F[_]: Sync](xdh: XDH, privateKeyBytes: ByteVector, provider: Option[Provider | JProvider])
  : F[XECPrivateKey] =
    KeyFactory.generatePrivate[F](XDH, privateKeySpec(xdh, privateKeyBytes), provider).map(_.asInstanceOf[XECPrivateKey])

  def getParameterSpecName(publicKey: XECPublicKey): Either[SecurityError, XDH] =
    publicKey.getParams match
      case namedParameterSpec: JNamedParameterSpec =>
        namedParameterSpec.getName match
          case X25519.algorithm => X25519.asRight
          case X448.algorithm => X448.asRight
          case algorithm => UnknownParameterSpecName(algorithm).asLeft
      case params => UncheckedParameterSpec(using scala.reflect.ClassTag(params.getClass)).asLeft

  def rawPublicKey(publicKey: XECPublicKey): Either[SecurityError, ByteVector] =
    getParameterSpecName(publicKey).map(xdh =>
      BigInt(publicKey.getU).mod(xdh.prime).toByteVector.reverse.adjustLength(xdh.keyByteLength)
    )

  def rawPrivateKey(privateKey: XECPrivateKey): ByteVector =
    privateKey.getScalar.toScala.fold(ByteVector.empty)(ByteVector.apply)
end XDHCompanion
