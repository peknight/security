package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}

trait AES_192 extends AES_N:
  override val keySize: Int = 192
  override def /(mode: CipherAlgorithmMode): This = AES_192(mode, padding)
  override def /(padding: CipherAlgorithmPadding): This = AES_192(mode, padding)
end AES_192
object AES_192 extends AES_192:
  private case class AES_192(override val mode: CipherAlgorithmMode, override val padding: CipherAlgorithmPadding)
    extends com.peknight.security.cipher.AES_192
  def apply(mode: CipherAlgorithmMode = NONE, padding: CipherAlgorithmPadding = NoPadding)
  : com.peknight.security.cipher.AES_192 =
    (mode, padding) match
      case (NONE, NoPadding) => this
      case _ => AES_192(mode, padding)
end AES_192
