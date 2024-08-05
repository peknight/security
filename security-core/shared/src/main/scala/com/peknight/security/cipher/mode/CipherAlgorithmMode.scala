package com.peknight.security.cipher.mode

trait CipherAlgorithmMode derives CanEqual:
  def mode: String
  override def toString: String = mode
end CipherAlgorithmMode
