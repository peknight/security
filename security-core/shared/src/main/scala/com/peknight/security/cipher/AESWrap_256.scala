package com.peknight.security.cipher

import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.CipherAlgorithmPadding

trait AESWrap_256 extends AESWrap_N:
  override val keySize: Int = 256
  override def /(mode: CipherAlgorithmMode): This = AES_256(mode, padding)
  override def /(padding: CipherAlgorithmPadding): This = AES_256(mode, padding)
end AESWrap_256
object AESWrap_256 extends AESWrap_256
