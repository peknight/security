package com.peknight.security.cipher

import com.peknight.security.cipher.mode.{CipherAlgorithmMode, KWP}

trait AESWrapPad extends AES:
  override val mode: CipherAlgorithmMode = KWP
  override def transformation: String = algorithm
  override def algorithm: String = "AESWrapPad"
end AESWrapPad
object AESWrapPad extends AESWrapPad
