package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Koblitz
import com.peknight.security.ecc.field.Prime

trait secp224k1 extends StandardsForEfficientCryptography with secp224k1Platform with Prime with Koblitz:
  def bitLength: Int = 224
  def curveOrder: Int = 1
end secp224k1
object secp224k1 extends secp224k1
