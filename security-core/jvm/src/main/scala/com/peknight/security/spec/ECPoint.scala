package com.peknight.security.spec

import java.security.spec.ECPoint as JECPoint

object ECPoint:
  def apply(x: BigInt, y: BigInt): JECPoint = new JECPoint(x.bigInteger, y.bigInteger)
end ECPoint
