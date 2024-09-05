package com.peknight.security.cipher.mode

/**
 * Key Wrap (KW) mode
 */
trait KW extends CipherAlgorithmMode:
  def mode: String = "KW"
end KW
object KW extends KW
