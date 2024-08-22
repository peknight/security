package com.peknight.security.spec

import java.security.spec.{ECParameterSpec, ECPrivateKeySpec as JECPrivateKeySpec}

object ECPrivateKeySpec:
  def apply(s: BigInt, params: ECParameterSpec): JECPrivateKeySpec = new JECPrivateKeySpec(s.bigInteger, params)
end ECPrivateKeySpec

