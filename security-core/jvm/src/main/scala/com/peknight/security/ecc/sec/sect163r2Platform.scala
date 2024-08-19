package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldF2m, ECParameterSpec, ECPoint, EllipticCurve}

trait sect163r2Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldF2m(163, new BigInteger("11692013098647223345629478661730264157247460344009")),
        new BigInteger("1"),
        new BigInteger("2982236234343851336267446656627785008148015875581")
      ),
      new ECPoint(
        new BigInteger("5759917430716753942228907521556834309477856722486"),
        new BigInteger("1216722771297916786238928618659324865903148082417")
      ),
      new BigInteger("5846006549323611672814742442876390689256843201587"),
      2
    )
end sect163r2Platform

