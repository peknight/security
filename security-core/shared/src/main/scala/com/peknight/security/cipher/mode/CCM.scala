package com.peknight.security.cipher.mode

/**
 * Counter/CBC Mode
 */
trait CCM extends CipherAlgorithmMode:
  def mode: String = "CCM"
end CCM
object CCM extends CCM
