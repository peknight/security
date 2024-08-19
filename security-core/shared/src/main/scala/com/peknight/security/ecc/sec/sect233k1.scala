package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Koblitz
import com.peknight.security.ecc.field.Binary

trait sect233k1 extends StandardsForEfficientCryptography with sect233k1Platform with Binary with Koblitz:
  def bitLength: Int = 233
  def curveOrder: Int = 1
end sect233k1
object sect233k1 extends sect233k1
