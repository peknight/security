package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldFp, ECParameterSpec, ECPoint, EllipticCurve}

trait secp160k1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldFp(new BigInteger("1461501637330902918203684832716283019651637554291")),
        new BigInteger("0"),
        new BigInteger("7")
      ),
      new ECPoint(
        new BigInteger("338530205676502674729549372677647997389429898939"),
        new BigInteger("842365456698940303598009444920994870805149798382")
      ),
      new BigInteger("1461501637330902918203686915170869725397159163571"),
      1
    )
end secp160k1Platform

