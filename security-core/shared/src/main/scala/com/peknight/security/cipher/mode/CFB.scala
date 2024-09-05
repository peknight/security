package com.peknight.security.cipher.mode

/**
 * Cipher Feedback Mode
 */
trait CFB extends CipherAlgorithmMode:
  def mode: String = "CFB"
end CFB
object CFB extends CFB
