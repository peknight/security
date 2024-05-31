package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Binary

trait sect409r1 extends StandardsForEfficientCryptography with Binary with Random:
  def bitLength: Int = 409
  def curveOrder: Int = 1
end sect409r1
object sect409r1 extends sect409r1
