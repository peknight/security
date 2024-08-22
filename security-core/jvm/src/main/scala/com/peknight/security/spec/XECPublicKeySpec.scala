package com.peknight.security.spec

import java.security.spec.{AlgorithmParameterSpec, XECPublicKeySpec as JXECPublicKeySpec}

object XECPublicKeySpec:
  def apply(params: AlgorithmParameterSpec, u: BigInt): JXECPublicKeySpec = 
    new JXECPublicKeySpec(params, u.bigInteger)
end XECPublicKeySpec
