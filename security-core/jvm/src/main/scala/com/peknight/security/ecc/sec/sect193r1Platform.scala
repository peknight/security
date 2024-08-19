package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldF2m, ECParameterSpec, ECPoint, EllipticCurve}

trait sect193r1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldF2m(193, new BigInteger("12554203470773361527671578846415332832204710888928069058561")),
        new BigInteger("576751075026818752436662854952381179295973340004111842049"),
        new BigInteger("6227610566229294112017936480812099737256078134172818311188")
      ),
      new ECPoint(
        new BigInteger("12272390550309971036302743370064453956929989632374098413025"),
        new BigInteger("929037239281491062957629204723061153948896755298992003845")
      ),
      new BigInteger("6277101735386680763835789423269548053691575186051040197193"),
      2
    )
end sect193r1Platform

