package com.peknight.security.cipher.mode

/**
 * Counter/CBC Mode
 */
trait CCM extends CipherAlgorithmMode:
  val mode: String = "CCM"
end CCM
object CCM extends CCM
