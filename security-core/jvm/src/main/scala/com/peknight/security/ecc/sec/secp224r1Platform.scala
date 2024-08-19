package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldFp, ECParameterSpec, ECPoint, EllipticCurve}

trait secp224r1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldFp(new BigInteger("26959946667150639794667015087019630673557916260026308143510066298881")),
        new BigInteger("26959946667150639794667015087019630673557916260026308143510066298878"),
        new BigInteger("18958286285566608000408668544493926415504680968679321075787234672564")
      ),
      new ECPoint(
        new BigInteger("19277929113566293071110308034699488026831934219452440156649784352033"),
        new BigInteger("19926808758034470970197974370888749184205991990603949537637343198772")
      ),
      new BigInteger("26959946667150639794667015087019625940457807714424391721682722368061"),
      1
    )
end secp224r1Platform

