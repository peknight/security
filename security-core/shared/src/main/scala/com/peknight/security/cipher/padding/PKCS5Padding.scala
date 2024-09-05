package com.peknight.security.cipher.padding

/**
 * The padding scheme described in PKCS #5: Password-Based Cryptography Specification, version 2.1.
 */
trait PKCS5Padding extends PKCSPadding:
  def padding: String = "PKCS5Padding"
end PKCS5Padding
object PKCS5Padding extends PKCS5Padding
