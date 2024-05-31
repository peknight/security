package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Binary

trait sect163r2 extends StandardsForEfficientCryptography with Binary with Random:
  def bitLength: Int = 163
  def curveOrder: Int = 2
end sect163r2
object sect163r2 extends sect163r2
