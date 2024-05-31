package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Binary

trait sect193r1 extends StandardsForEfficientCryptography with Binary with Random:
  def bitLength: Int = 193
  def curveOrder: Int = 1
end sect193r1
object sect193r1 extends sect193r1
