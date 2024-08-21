package com.peknight.security.spec

import java.security.spec.EdECPoint as JEdECPoint

object EdECPoint:
  def apply(xOdd: Boolean, y: BigInt): JEdECPoint = new JEdECPoint(xOdd, y.bigInteger)
end EdECPoint
