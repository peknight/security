package com.peknight.security.syntax

import com.peknight.security.cipher.RSA
import scodec.bits.ByteVector

import java.security.interfaces.{RSAKey, RSAPrivateCrtKey, RSAPrivateKey, RSAPublicKey}

trait RSAKeySyntax:
  extension (key: RSAKey)
    def rawModulus: ByteVector = RSA.rawModulus(key)
  end extension
  extension (publicKey: RSAPublicKey)
    def rawPublicExponent: ByteVector = RSA.rawPublicExponent(publicKey)
  end extension
  extension (privateKey: RSAPrivateKey)
    def rawPrivateExponent: ByteVector = RSA.rawPrivateExponent(privateKey)
    def rawPrimePOption: Option[ByteVector] = RSA.rawPrimePOption(privateKey)
    def rawPrimeQOption: Option[ByteVector] = RSA.rawPrimeQOption(privateKey)
    def rawPrimeExponentPOption: Option[ByteVector] = RSA.rawPrimeExponentPOption(privateKey)
    def rawPrimeExponentQOption: Option[ByteVector] = RSA.rawPrimeExponentQOption(privateKey)
    def rawCrtCoefficientOption: Option[ByteVector] = RSA.rawCrtCoefficientOption(privateKey)
  end extension
  extension (privateKey: RSAPrivateCrtKey)
    def rawPrimeP: ByteVector = RSA.rawPrimeP(privateKey)
    def rawPrimeQ: ByteVector = RSA.rawPrimeQ(privateKey)
    def rawPrimeExponentP: ByteVector = RSA.rawPrimeExponentP(privateKey)
    def rawPrimeExponentQ: ByteVector = RSA.rawPrimeExponentQ(privateKey)
    def rawCrtCoefficient: ByteVector = RSA.rawCrtCoefficient(privateKey)
  end extension
end RSAKeySyntax
