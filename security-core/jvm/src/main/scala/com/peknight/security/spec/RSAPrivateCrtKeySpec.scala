package com.peknight.security.spec

import java.security.spec.RSAPrivateCrtKeySpec as JRSAPrivateCrtKeySpec

object RSAPrivateCrtKeySpec:
  def apply(modulus: BigInt, publicExponent: BigInt, privateExponent: BigInt, primeP: BigInt, primeQ: BigInt,
            primeExponentP: BigInt, primeExponentQ: BigInt, crtCoefficient: BigInt): JRSAPrivateCrtKeySpec =
    new JRSAPrivateCrtKeySpec(modulus.bigInteger, publicExponent.bigInteger, privateExponent.bigInteger,
      primeP.bigInteger, primeQ.bigInteger, primeExponentP.bigInteger, primeExponentQ.bigInteger,
      crtCoefficient.bigInteger)
end RSAPrivateCrtKeySpec
