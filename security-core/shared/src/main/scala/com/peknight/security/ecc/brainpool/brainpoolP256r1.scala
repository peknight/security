package com.peknight.security.ecc.brainpool

trait brainpoolP256r1 extends Brainpool with brainpoolP256r1Platform:
  def bitLength: Int = 256
  def curveOrder: Int = 1
end brainpoolP256r1
object brainpoolP256r1 extends brainpoolP256r1
