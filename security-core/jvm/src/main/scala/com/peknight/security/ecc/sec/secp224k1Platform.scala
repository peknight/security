package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldFp, ECParameterSpec, ECPoint, EllipticCurve}

trait secp224k1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldFp(new BigInteger("26959946667150639794667015087019630673637144422540572481099315275117")),
        new BigInteger("0"),
        new BigInteger("5")
      ),
      new ECPoint(
        new BigInteger("16983810465656793445178183341822322175883642221536626637512293983324"),
        new BigInteger("13272896753306862154536785447615077600479862871316829862783613755813")
      ),
      new BigInteger("26959946667150639794667015087019640346510327083120074548994958668279"),
      1
    )
end secp224k1Platform

