package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldF2m, ECParameterSpec, ECPoint, EllipticCurve}

trait sect163r1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldF2m(163, new BigInteger("11692013098647223345629478661730264157247460344009")),
        new BigInteger("11272584574060402170600355401469405585559711656674"),
        new BigInteger("10341149448350347985759700389662805134872097107929")
      ),
      new ECPoint(
        new BigInteger("4987329473907365857178124865428460464972118795860"),
        new BigInteger("384617752061712164277996110850745784319273334915")
      ),
      new BigInteger("5846006549323611672814738465098798981304420411291"),
      2
    )
end sect163r1Platform

