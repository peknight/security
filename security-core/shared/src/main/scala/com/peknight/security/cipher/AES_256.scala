package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}

trait AES_256 extends AES_N:
  override val blockSize: Int = 256 / 8
  override def /(mode: CipherAlgorithmMode): This = AES_256(mode, padding)
  override def /(padding: CipherAlgorithmPadding): This = AES_256(mode, padding)
end AES_256
object AES_256 extends AES_256:
  private case class AES_256(override val mode: CipherAlgorithmMode, override val padding: CipherAlgorithmPadding)
    extends com.peknight.security.cipher.AES_256
  def apply(mode: CipherAlgorithmMode = NONE, padding: CipherAlgorithmPadding = NoPadding)
  : com.peknight.security.cipher.AES_256 =
    (mode, padding) match
      case (NONE, NoPadding) => this
      case _ => AES_256(mode, padding)
end AES_256
