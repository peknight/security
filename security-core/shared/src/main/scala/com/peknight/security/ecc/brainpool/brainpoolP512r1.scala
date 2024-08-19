package com.peknight.security.ecc.brainpool

trait brainpoolP512r1 extends Brainpool with brainpoolP512r1Platform:
  def bitLength: Int = 512
  def curveOrder: Int = 1
end brainpoolP512r1
object brainpoolP512r1 extends brainpoolP512r1
