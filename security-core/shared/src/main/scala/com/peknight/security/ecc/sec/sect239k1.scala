package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Koblitz
import com.peknight.security.ecc.field.Binary

trait sect239k1 extends StandardsForEfficientCryptography with sect239k1Platform with Binary with Koblitz:
  def bitLength: Int = 239
  def curveOrder: Int = 1
  override def paramsBitLength: Int = 238
end sect239k1
object sect239k1 extends sect239k1
