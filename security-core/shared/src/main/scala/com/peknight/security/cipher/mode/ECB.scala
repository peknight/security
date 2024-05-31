package com.peknight.security.cipher.mode

/**
 * Electronic Codebook Mode
 */
trait ECB extends CipherAlgorithmMode:
  val mode: String = "ECB"
end ECB
object ECB extends ECB
