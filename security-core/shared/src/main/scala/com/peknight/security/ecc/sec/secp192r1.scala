package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Prime

trait secp192r1 extends StandardsForEfficientCryptography with secp192r1Platform with Prime with Random:
  def bitLength: Int = 192
  def curveOrder: Int = 1
end secp192r1
object secp192r1 extends secp192r1
