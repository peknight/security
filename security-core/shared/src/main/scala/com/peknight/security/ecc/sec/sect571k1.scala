package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Koblitz
import com.peknight.security.ecc.field.Binary

trait sect571k1 extends StandardsForEfficientCryptography with sect571k1Platform with Binary with Koblitz:
  def bitLength: Int = 571
  def curveOrder: Int = 1
end sect571k1
object sect571k1 extends sect571k1
