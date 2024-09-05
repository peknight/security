package com.peknight.security.cipher.padding

import com.peknight.security.padding.`PKCS1-v1_5`

/**
 * The padding scheme described in PKCS #1 v2.2, used with the RSA algorithm.
 */
trait PKCS1Padding extends PKCSPadding with `PKCS1-v1_5`:
  override def padding: String = "PKCS1Padding"
end PKCS1Padding
object PKCS1Padding extends PKCS1Padding
