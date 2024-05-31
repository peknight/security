package com.peknight.security.cipher.mode

/**
 * Cipher Text Stealing, as described in Bruce Schneier's book Applied Cryptography-Second Edition, John Wiley and Sons, 1996.
 */
trait CTS extends CipherAlgorithmMode:
  val mode: String = "CTS"
end CTS
object CTS extends CTS
