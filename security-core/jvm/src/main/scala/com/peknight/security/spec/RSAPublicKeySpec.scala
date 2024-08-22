package com.peknight.security.spec

import java.security.spec.RSAPublicKeySpec as JRSAPublicKeySpec

object RSAPublicKeySpec:
  def apply(modulus: BigInt, publicExponent: BigInt): JRSAPublicKeySpec =
    new JRSAPublicKeySpec(modulus.bigInteger, publicExponent.bigInteger)
end RSAPublicKeySpec
