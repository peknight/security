package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Binary

trait sect233r1 extends StandardsForEfficientCryptography with sect233r1Platform with Binary with Random:
  def bitLength: Int = 233
  def curveOrder: Int = 1
end sect233r1
object sect233r1 extends sect233r1
