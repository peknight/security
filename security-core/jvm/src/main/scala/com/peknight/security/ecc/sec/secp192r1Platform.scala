package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldFp, ECParameterSpec, ECPoint, EllipticCurve}

trait secp192r1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldFp(new BigInteger("6277101735386680763835789423207666416083908700390324961279")),
        new BigInteger("6277101735386680763835789423207666416083908700390324961276"),
        new BigInteger("2455155546008943817740293915197451784769108058161191238065")
      ),
      new ECPoint(
        new BigInteger("602046282375688656758213480587526111916698976636884684818"),
        new BigInteger("174050332293622031404857552280219410364023488927386650641")
      ),
      new BigInteger("6277101735386680763835789423176059013767194773182842284081"),
      1
    )
end secp192r1Platform

