package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Koblitz
import com.peknight.security.ecc.field.Binary

trait sect283k1 extends StandardsForEfficientCryptography with sect283k1Platform with Binary with Koblitz:
  def bitLength: Int = 283
  def curveOrder: Int = 1
  override def paramsBitLength: Int = 281
end sect283k1
object sect283k1 extends sect283k1
