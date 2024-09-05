package com.peknight.security.cipher.mode

/**
 * Propagating Cipher Block Chaining
 */
trait PCBC extends CipherAlgorithmMode:
  def mode: String = "PCBC"
end PCBC
object PCBC extends PCBC
