package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Prime

trait secp521r1 extends StandardsForEfficientCryptography with Prime with Random:
  def bitLength: Int = 521
  def curveOrder: Int = 1
end secp521r1
object secp521r1 extends secp521r1
