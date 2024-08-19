package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldFp, ECParameterSpec, ECPoint, EllipticCurve}

trait secp256r1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldFp(new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951")),
        new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853948"),
        new BigInteger("41058363725152142129326129780047268409114441015993725554835256314039467401291")
      ),
      new ECPoint(
        new BigInteger("48439561293906451759052585252797914202762949526041747995844080717082404635286"),
        new BigInteger("36134250956749795798585127919587881956611106672985015071877198253568414405109")
      ),
      new BigInteger("115792089210356248762697446949407573529996955224135760342422259061068512044369"),
      1
    )
end secp256r1Platform

