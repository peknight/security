package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}

trait AES_128 extends AES_N:
  override def /(mode: CipherAlgorithmMode): This = AES_128(mode, padding)
  override def /(padding: CipherAlgorithmPadding): This = AES_128(mode, padding)
end AES_128
object AES_128 extends AES_128:
  private[this] case class AES_128(override val mode: CipherAlgorithmMode, override val padding: CipherAlgorithmPadding)
    extends com.peknight.security.cipher.AES_128
  def apply(mode: CipherAlgorithmMode = NONE, padding: CipherAlgorithmPadding = NoPadding)
  : com.peknight.security.cipher.AES_128 =
    (mode, padding) match
      case (NONE, NoPadding) => this
      case _ => AES_128(mode, padding)
end AES_128
