package com.peknight.security.ecc.brainpool

trait brainpoolP384r1 extends Brainpool with brainpoolP384r1Platform:
  def bitLength: Int = 384
  def curveOrder: Int = 1
end brainpoolP384r1
object brainpoolP384r1 extends brainpoolP384r1
