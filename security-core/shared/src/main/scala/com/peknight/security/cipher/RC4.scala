package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.CipherAlgorithm
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}

/**
 * RC4 not recommend
 */
trait RC4 extends ARCFOUR:
  type This = RC4
  def /(mode: CipherAlgorithmMode): This = RC4(mode, padding)
  def /(padding: CipherAlgorithmPadding): This = RC4(mode, padding)
  override val algorithm: String = "RC4"
end RC4
object RC4 extends RC4:
  private case class RC4(override val mode: CipherAlgorithmMode, override val padding: CipherAlgorithmPadding)
    extends com.peknight.security.cipher.RC4
  def apply(mode: CipherAlgorithmMode = NONE, padding: CipherAlgorithmPadding = NoPadding)
  : com.peknight.security.cipher.RC4 =
    (mode, padding) match
      case (NONE, NoPadding) => this
      case _ => RC4(mode, padding)
end RC4
