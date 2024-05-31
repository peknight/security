package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Binary

trait sect193r2 extends StandardsForEfficientCryptography with Binary with Random:
  def bitLength: Int = 193
  def curveOrder: Int = 2
end sect193r2
object sect193r2 extends sect193r2
