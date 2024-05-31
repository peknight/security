package com.peknight.security.cipher.padding

/**
 * The padding scheme described in PKCS #1 v2.2, used with the RSA algorithm.
 */
trait PKCS1Padding extends PKCSPadding:
  val padding: String = "PKCS1Padding"
end PKCS1Padding
object PKCS1Padding extends PKCS1Padding
