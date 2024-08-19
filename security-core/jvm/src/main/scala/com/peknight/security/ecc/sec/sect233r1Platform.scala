package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldF2m, ECParameterSpec, ECPoint, EllipticCurve}

trait sect233r1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldF2m(233, new BigInteger("13803492693581127574869511724554050904902217944359662576256527028453377")),
        new BigInteger("1"),
        new BigInteger("2760497980029204187078845502377898520307707256259003964398570147123373")
      ),
      new ECPoint(
        new BigInteger("6761246501583409083997096882159824046681246465812468867444643442021771"),
        new BigInteger("6912913004411390932094889411904587007871508723951293564567204383952978")
      ),
      new BigInteger("6901746346790563787434755862277025555839812737345013555379383634485463"),
      2
    )
end sect233r1Platform

