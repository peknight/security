package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Binary

trait sect163r1 extends StandardsForEfficientCryptography with Binary with Random:
  def bitLength: Int = 163
  def curveOrder: Int = 1
end sect163r1
object sect163r1 extends sect163r1
