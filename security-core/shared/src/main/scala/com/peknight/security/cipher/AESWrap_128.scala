package com.peknight.security.cipher

import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.CipherAlgorithmPadding

trait AESWrap_128 extends AESWrap_N:
  override def /(mode: CipherAlgorithmMode): This = AES_128(mode, padding)
  override def /(padding: CipherAlgorithmPadding): This = AES_128(mode, padding)
end AESWrap_128
object AESWrap_128 extends AESWrap_128

