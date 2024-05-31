package com.peknight.security.cipher

trait AESWrap_N extends AESWrap with AES_N:
  override def algorithm: String = s"AESWrap_$keySize"
end AESWrap_N
