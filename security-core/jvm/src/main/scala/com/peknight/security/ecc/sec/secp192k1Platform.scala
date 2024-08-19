package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldFp, ECParameterSpec, ECPoint, EllipticCurve}

trait secp192k1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldFp(new BigInteger("6277101735386680763835789423207666416102355444459739541047")),
        new BigInteger("0"),
        new BigInteger("3")
      ),
      new ECPoint(
        new BigInteger("5377521262291226325198505011805525673063229037935769709693"),
        new BigInteger("3805108391982600717572440947423858335415441070543209377693")
      ),
      new BigInteger("6277101735386680763835789423061264271957123915200845512077"),
      1
    )
end secp192k1Platform

