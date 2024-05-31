package com.peknight.security.cipher

trait AES_N extends AES:
  override def algorithm: String = s"AES_$keySize"
end AES_N
