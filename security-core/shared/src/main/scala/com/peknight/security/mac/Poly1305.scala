package com.peknight.security.mac

import com.peknight.security.algorithm.Algorithm

trait Poly1305 extends MAC:
  val algorithm: String = "Poly1305"
end Poly1305
object Poly1305 extends Poly1305
