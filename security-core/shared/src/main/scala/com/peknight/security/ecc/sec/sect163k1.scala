package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Koblitz
import com.peknight.security.ecc.field.Binary

trait sect163k1 extends StandardsForEfficientCryptography with Binary with Koblitz:
  def bitLength: Int = 163
  def curveOrder: Int = 1
end sect163k1
object sect163k1 extends sect163k1
