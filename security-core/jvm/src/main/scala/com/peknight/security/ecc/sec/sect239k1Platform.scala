package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldF2m, ECParameterSpec, ECPoint, EllipticCurve}

trait sect239k1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldF2m(239, new BigInteger("883423532389192164791649115746868590639471499359017658131558014629445633")),
        new BigInteger("0"),
        new BigInteger("1")
      ),
      new ECPoint(
        new BigInteger("287304427851433003189509051221031978591368025490899286200762613294446044"),
        new BigInteger("815727950839377703994180670110555770834903050527325106707102047979958474")
      ),
      new BigInteger("220855883097298041197912187592864814948216561321709848887480219215362213"),
      4
    )
end sect239k1Platform

