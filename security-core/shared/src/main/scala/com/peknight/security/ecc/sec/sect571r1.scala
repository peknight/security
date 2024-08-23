package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Binary

trait sect571r1 extends StandardsForEfficientCryptography with sect571r1Platform with Binary with Random:
  def bitLength: Int = 571
  def curveOrder: Int = 1
  override def paramsBitLength: Int = 570
end sect571r1
object sect571r1 extends sect571r1
