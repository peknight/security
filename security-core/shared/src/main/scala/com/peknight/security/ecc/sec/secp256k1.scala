package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Koblitz
import com.peknight.security.ecc.field.Prime

trait secp256k1 extends StandardsForEfficientCryptography with Prime with Koblitz:
  def bitLength: Int = 256
  def curveOrder: Int = 1
end secp256k1
object secp256k1 extends secp256k1
