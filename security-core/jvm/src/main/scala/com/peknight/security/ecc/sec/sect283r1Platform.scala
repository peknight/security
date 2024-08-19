package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldF2m, ECParameterSpec, ECPoint, EllipticCurve}

trait sect283r1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldF2m(283, new BigInteger("15541351137805832567355695254588151253139254712417116170014499277911234281641667989665")),
        new BigInteger("1"),
        new BigInteger("4821813576056072374006997780399081180312270030300601270120450341205914644378616963829")
      ),
      new ECPoint(
        new BigInteger("11604587487407003699882500449177537465719784002620028212980871291231978603047872962643"),
        new BigInteger("6612720053854191978412609357563545875491153188501906352980899759345275170452624446196")
      ),
      new BigInteger("7770675568902916283677847627294075626569625924376904889109196526770044277787378692871"),
      2
    )
end sect283r1Platform

