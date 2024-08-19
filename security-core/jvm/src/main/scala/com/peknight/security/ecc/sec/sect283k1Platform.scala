package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldF2m, ECParameterSpec, ECPoint, EllipticCurve}

trait sect283k1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldF2m(283, new BigInteger("15541351137805832567355695254588151253139254712417116170014499277911234281641667989665")),
        new BigInteger("0"),
        new BigInteger("1")
      ),
      new ECPoint(
        new BigInteger("9737095673315832344313391497449387731784428326114441977662399932694280557468376967222"),
        new BigInteger("3497201781826516614681192670485202061196189998012192335594744939847890291586353668697")
      ),
      new BigInteger("3885337784451458141838923813647037813284811733793061324295874997529815829704422603873"),
      4
    )
end sect283k1Platform

