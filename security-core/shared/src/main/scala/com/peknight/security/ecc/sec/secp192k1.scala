package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Koblitz
import com.peknight.security.ecc.field.Prime

trait secp192k1 extends StandardsForEfficientCryptography with Prime with Koblitz:
  def bitLength: Int = 192
  def curveOrder: Int = 1
end secp192k1
object secp192k1 extends secp192k1
