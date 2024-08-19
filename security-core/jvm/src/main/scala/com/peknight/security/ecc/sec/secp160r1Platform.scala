package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldFp, ECParameterSpec, ECPoint, EllipticCurve}

trait secp160r1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldFp(new BigInteger("1461501637330902918203684832716283019653785059327")),
        new BigInteger("1461501637330902918203684832716283019653785059324"),
        new BigInteger("163235791306168110546604919403271579530548345413")
      ),
      new ECPoint(
        new BigInteger("425826231723888350446541592701409065913635568770"),
        new BigInteger("203520114162904107873991457957346892027982641970")
      ),
      new BigInteger("1461501637330902918203687197606826779884643492439"),
      1
    )
end secp160r1Platform

