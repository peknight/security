package com.peknight.security.cipher

trait AES_N extends AES:
  override def algorithm: String = s"AES_${blockSize * 8}"
end AES_N
