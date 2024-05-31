package com.peknight.security.cipher

import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.CipherAlgorithmPadding

trait AESWrapPad_128 extends AESWrapPad_N:
  override def /(mode: CipherAlgorithmMode): This = AES_128(mode, padding)
  override def /(padding: CipherAlgorithmPadding): This = AES_128(mode, padding)
end AESWrapPad_128
object AESWrapPad_128 extends AESWrapPad_128
