package com.peknight.security.key.kdf

import com.peknight.security.mac.Hmac

/**
 * HMAC-based KDF as defined in RFC 5869.
 */
trait HmacBasedKDF extends KDFAlgorithm:
  def mac: Hmac
  def algorithm: String = s"HKDF-${mac.digest.abbreviation}"
end HmacBasedKDF
