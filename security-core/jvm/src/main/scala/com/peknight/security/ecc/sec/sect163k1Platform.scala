package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldF2m, ECParameterSpec, ECPoint, EllipticCurve}

trait sect163k1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldF2m(163, new BigInteger("11692013098647223345629478661730264157247460344009")),
        new BigInteger("1"),
        new BigInteger("1")
      ),
      new ECPoint(
        new BigInteger("4373527398576640063579304354969275615843559206632"),
        new BigInteger("3705292482178961271312284701371585420180764402649")
      ),
      new BigInteger("5846006549323611672814741753598448348329118574063"),
      2
    )
end sect163k1Platform

