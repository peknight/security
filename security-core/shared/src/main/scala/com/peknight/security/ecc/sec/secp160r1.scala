package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Prime

trait secp160r1 extends StandardsForEfficientCryptography with secp160r1Platform with Prime with Random:
  def bitLength: Int = 160
  def curveOrder: Int = 1
  override def paramsBitLength: Int = 161
end secp160r1
object secp160r1 extends secp160r1
