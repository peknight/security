package com.peknight.security.cipher

trait AESWrapPad_N extends AESWrapPad with AES_N:
  override def algorithm: String = s"AESWrapPad_${blockSize * 8}"
end AESWrapPad_N
