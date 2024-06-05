package com.peknight.security.cipher

import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.CipherAlgorithmPadding

trait AESWrapPad_192 extends AESWrapPad_N:
  override val blockSize: Int = 192 / 8
  override def /(mode: CipherAlgorithmMode): This = AES_192(mode, padding)
  override def /(padding: CipherAlgorithmPadding): This = AES_192(mode, padding)
end AESWrapPad_192
object AESWrapPad_192 extends AESWrapPad_192
