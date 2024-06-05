package com.peknight.security.cipher

import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.CipherAlgorithmPadding

trait AESWrapPad_256 extends AESWrapPad_N:
  override val blockSize: Int = 256 / 8
  override def /(mode: CipherAlgorithmMode): This = AES_256(mode, padding)
  override def /(padding: CipherAlgorithmPadding): This = AES_256(mode, padding)
end AESWrapPad_256
object AESWrapPad_256 extends AESWrapPad_256
