package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Binary

trait sect283r1 extends StandardsForEfficientCryptography with sect283r1Platform with Binary with Random:
  def bitLength: Int = 283
  def curveOrder: Int = 1
end sect283r1
object sect283r1 extends sect283r1
