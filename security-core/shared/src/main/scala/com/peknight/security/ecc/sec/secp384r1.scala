package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Prime

trait secp384r1 extends StandardsForEfficientCryptography with Prime with Random:
  def bitLength: Int = 384
  def curveOrder: Int = 1
end secp384r1
object secp384r1 extends secp384r1
