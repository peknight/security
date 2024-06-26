package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Koblitz
import com.peknight.security.ecc.field.Binary

trait sect409k1 extends StandardsForEfficientCryptography with Binary with Koblitz:
  def bitLength: Int = 409
  def curveOrder: Int = 1
end sect409k1
object sect409k1 extends sect409k1
