package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Prime

trait secp256r1 extends StandardsForEfficientCryptography with Prime with Random:
  def bitLength: Int = 256
  def curveOrder: Int = 1
end secp256r1
object secp256r1 extends secp256r1
