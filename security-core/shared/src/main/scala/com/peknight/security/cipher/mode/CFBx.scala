package com.peknight.security.cipher.mode

trait CFBx extends CFB:
  def bits: Int
  override val mode: String = s"CFB$bits"
end CFBx
object CFBx:
  private case class CFBx(bits: Int) extends com.peknight.security.cipher.mode.CFBx
  def apply(bits: Int): com.peknight.security.cipher.mode.CFBx = CFBx(bits)
end CFBx
