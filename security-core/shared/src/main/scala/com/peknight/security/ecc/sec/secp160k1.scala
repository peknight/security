package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Koblitz
import com.peknight.security.ecc.field.Prime

trait secp160k1 extends StandardsForEfficientCryptography with secp160k1Platform with Prime with Koblitz:
  def bitLength: Int = 160
  def curveOrder: Int = 1
  override def paramsBitLength: Int = 161
end secp160k1
object secp160k1 extends secp160k1
