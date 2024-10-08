package com.peknight.security.key.kem

/**
 * DH-Based KEM as defined in RFC 9180.
 */
trait DHKEM extends KEMAlgorithm:
  def algorithm: String = "DHKEM"
end DHKEM
object DHKEM extends DHKEM
