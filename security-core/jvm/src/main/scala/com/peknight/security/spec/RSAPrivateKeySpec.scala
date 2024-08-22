package com.peknight.security.spec

import java.security.spec.RSAPrivateKeySpec as JRSAPrivateKeySpec

object RSAPrivateKeySpec:
  def apply(modulus: BigInt, privateExponent: BigInt): JRSAPrivateKeySpec =
    new JRSAPrivateKeySpec(modulus.bigInteger, privateExponent.bigInteger)
end RSAPrivateKeySpec
