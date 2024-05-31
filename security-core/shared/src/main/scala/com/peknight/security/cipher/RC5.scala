package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.CipherAlgorithm
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}

trait RC5 extends CipherAlgorithm with RC:
  type This = RC5
  def /(mode: CipherAlgorithmMode): This = RC5(mode, padding)
  def /(padding: CipherAlgorithmPadding): This = RC5(mode, padding)
  val algorithm: String = "RC5"
end RC5
object RC5 extends RC5:
  private[this] case class RC5(override val mode: CipherAlgorithmMode, override val padding: CipherAlgorithmPadding)
    extends com.peknight.security.cipher.RC5
  def apply(mode: CipherAlgorithmMode = NONE, padding: CipherAlgorithmPadding = NoPadding)
  : com.peknight.security.cipher.RC5 =
    (mode, padding) match
      case (NONE, NoPadding) => this
      case _ => RC5(mode, padding)
end RC5
