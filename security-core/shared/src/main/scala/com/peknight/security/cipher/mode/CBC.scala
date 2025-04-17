package com.peknight.security.cipher.mode

/**
 * Cipher Block Chaining Mode
 */
trait CBC extends CipherAlgorithmMode:
  def mode: String = "CBC"
end CBC
object CBC extends CBC with CBCCompanion
