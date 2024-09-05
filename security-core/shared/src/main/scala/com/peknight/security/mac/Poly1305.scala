package com.peknight.security.mac

trait Poly1305 extends MAC:
  def algorithm: String = "Poly1305"
end Poly1305
object Poly1305 extends Poly1305
