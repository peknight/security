package com.peknight.security.cipher

import com.peknight.security.cipher.mode.{CipherAlgorithmMode, KW}

trait AESWrap extends AES:
  override val mode: CipherAlgorithmMode = KW
  override val transformation: String = algorithm
  override def algorithm: String = "AESWrap"
end AESWrap
object AESWrap extends AESWrap
