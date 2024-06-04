package com.peknight.security.cipher.mode

trait OFBx extends OFB:
  def bits: Int
  override val mode: String = s"OFB$bits"
end OFBx
object OFBx:
  private case class OFBx(bits: Int) extends com.peknight.security.cipher.mode.OFBx
  def apply(bits: Int): com.peknight.security.cipher.mode.OFBx = OFBx(bits)
end OFBx
