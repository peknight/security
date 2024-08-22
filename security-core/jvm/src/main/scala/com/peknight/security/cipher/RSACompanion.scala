package com.peknight.security.cipher

import cats.effect.Sync
import cats.syntax.functor.*
import com.peknight.scodec.bits.ext.syntax.bigInt.toUnsignedBytes
import com.peknight.security.provider.Provider
import com.peknight.security.spec.{RSAPrivateCrtKeySpec, RSAPrivateKeySpec, RSAPublicKeySpec}
import scodec.bits.ByteVector

import java.math.BigInteger
import java.security.Provider as JProvider
import java.security.interfaces.{RSAKey, RSAPrivateCrtKey, RSAPrivateKey, RSAPublicKey}

trait RSACompanion:
  def publicKey[F[_]: Sync](modulus: BigInt, publicExponent: BigInt, provider: Option[Provider | JProvider] = None)
  : F[RSAPublicKey] =
    RSA.generatePublic[F](RSAPublicKeySpec(modulus, publicExponent), provider).map(_.asInstanceOf[RSAPublicKey])

  def privateKey[F[_]: Sync](modulus: BigInt, privateExponent: BigInt, provider: Option[Provider | JProvider] = None)
  : F[RSAPrivateKey] =
    RSA.generatePrivate[F](RSAPrivateKeySpec(modulus, privateExponent), provider).map(_.asInstanceOf[RSAPrivateKey])

  def privateCrtKey[F[_]: Sync](modulus: BigInt, publicExponent: BigInt, privateExponent: BigInt, primeP: BigInt,
                                primeQ: BigInt, primeExponentP: BigInt, primeExponentQ: BigInt, crtCoefficient: BigInt,
                                provider: Option[Provider | JProvider] = None): F[RSAPrivateCrtKey] =
    RSA.generatePrivate[F](RSAPrivateCrtKeySpec(modulus, publicExponent, privateExponent, primeP, primeQ,
      primeExponentP, primeExponentQ, crtCoefficient), provider).map(_.asInstanceOf[RSAPrivateCrtKey])

  extension [K <: RSAKey](key: K)
    private def rawBytes(f: K => BigInteger): ByteVector = BigInt(f(key)).toUnsignedBytes
  end extension

  def rawModulus(publicKey: RSAKey): ByteVector = publicKey.rawBytes(_.getModulus)
  def rawPublicExponent(publicKey: RSAPublicKey): ByteVector = publicKey.rawBytes(_.getPublicExponent)
  def rawPrivateExponent(privateKey: RSAPrivateKey): ByteVector = privateKey.rawBytes(_.getPrivateExponent)

  def rawPrimeP(privateKey: RSAPrivateCrtKey): ByteVector = privateKey.rawBytes(_.getPrimeP)
  def rawPrimeQ(privateKey: RSAPrivateCrtKey): ByteVector = privateKey.rawBytes(_.getPrimeQ)
  def rawPrimeExponentP(privateKey: RSAPrivateCrtKey): ByteVector = privateKey.rawBytes(_.getPrimeExponentP)
  def rawPrimeExponentQ(privateKey: RSAPrivateCrtKey): ByteVector = privateKey.rawBytes(_.getPrimeExponentQ)
  def rawCrtCoefficient(privateKey: RSAPrivateCrtKey): ByteVector = privateKey.rawBytes(_.getCrtCoefficient)

  extension (privateKey: RSAPrivateKey)
    private def rawCrtBytes(f: RSAPrivateCrtKey => BigInteger): Option[ByteVector] =
      privateKey match
        case k: RSAPrivateCrtKey => Option(f(k)).map(i => BigInt(i).toUnsignedBytes)
        case _ => None
  end extension

  def rawPrimePOption(privateKey: RSAPrivateKey): Option[ByteVector] = privateKey.rawCrtBytes(_.getPrimeP)
  def rawPrimeQOption(privateKey: RSAPrivateKey): Option[ByteVector] = privateKey.rawCrtBytes(_.getPrimeQ)
  def rawPrimeExponentPOption(privateKey: RSAPrivateKey): Option[ByteVector] = privateKey.rawCrtBytes(_.getPrimeExponentP)
  def rawPrimeExponentQOption(privateKey: RSAPrivateKey): Option[ByteVector] = privateKey.rawCrtBytes(_.getPrimeExponentQ)
  def rawCrtCoefficientOption(privateKey: RSAPrivateKey): Option[ByteVector] = privateKey.rawCrtBytes(_.getCrtCoefficient)
end RSACompanion
