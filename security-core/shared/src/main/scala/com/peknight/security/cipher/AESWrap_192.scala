package com.peknight.security.cipher

import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.CipherAlgorithmPadding

trait AESWrap_192 extends AESWrap_N:
  override val blockSize: Int = 192 / 8
  override def /(mode: CipherAlgorithmMode): This = AES_192(mode, padding)
  override def /(padding: CipherAlgorithmPadding): This = AES_192(mode, padding)
end AESWrap_192
object AESWrap_192 extends AESWrap_192
