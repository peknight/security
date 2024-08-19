package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldF2m, ECParameterSpec, ECPoint, EllipticCurve}

trait sect233k1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldF2m(233, new BigInteger("13803492693581127574869511724554050904902217944359662576256527028453377")),
        new BigInteger("0"),
        new BigInteger("1")
      ),
      new ECPoint(
        new BigInteger("9980522611481012342443087688797002679043489582926858424680330554073382"),
        new BigInteger("12814767389816757102953168016268660157166792010263439198493421287958179")
      ),
      new BigInteger("3450873173395281893717377931138512760570940988862252126328087024741343"),
      4
    )
end sect233k1Platform

