package com.peknight.security.cipher

import com.peknight.security.algorithm.Algorithm
import com.peknight.security.padding.Padding

trait RSAES extends Algorithm:
  def padding: Padding
  def algorithm: String = s"RSAES-${padding.padding}"
end RSAES
object RSAES:
  private case class RSAES(padding: Padding) extends com.peknight.security.cipher.RSAES
  def apply(padding: Padding): com.peknight.security.cipher.RSAES = RSAES(padding)
  def -(padding: Padding): com.peknight.security.cipher.RSAES = apply(padding)
end RSAES
