package com.peknight.security.cipher.mode

/**
 * Propagating Cipher Block Chaining
 */
trait PCBC extends CipherAlgorithmMode:
  val mode: String = "PCBC"
end PCBC
object PCBC extends PCBC
