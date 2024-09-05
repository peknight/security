package com.peknight.security.cipher.mode

/**
 * Electronic Codebook Mode
 */
trait ECB extends CipherAlgorithmMode:
  def mode: String = "ECB"
end ECB
object ECB extends ECB
