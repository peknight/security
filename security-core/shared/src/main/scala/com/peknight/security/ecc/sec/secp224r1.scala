package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Prime

trait secp224r1 extends StandardsForEfficientCryptography with Prime with Random:
  def bitLength: Int = 224
  def curveOrder: Int = 1
end secp224r1
object secp224r1 extends secp224r1
