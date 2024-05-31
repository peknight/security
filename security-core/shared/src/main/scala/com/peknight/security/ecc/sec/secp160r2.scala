package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Prime

trait secp160r2 extends StandardsForEfficientCryptography with Prime with Random:
  def bitLength: Int = 160
  def curveOrder: Int = 2
end secp160r2
object secp160r2 extends secp160r2
