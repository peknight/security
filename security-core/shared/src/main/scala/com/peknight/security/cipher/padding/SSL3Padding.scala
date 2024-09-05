package com.peknight.security.cipher.padding

/**
 * The padding scheme defined in the SSL Protocol Version 3.0, November 18, 1996, section 5.2.3.2 (CBC block cipher)
 */
trait SSL3Padding extends CipherAlgorithmPadding:
  def padding: String = "SSL3Padding"
end SSL3Padding
object SSL3Padding extends SSL3Padding
