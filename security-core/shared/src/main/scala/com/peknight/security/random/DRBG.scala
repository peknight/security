package com.peknight.security.random

/**
 * An algorithm using DRBG mechanisms as defined in NIST SP 800-90Ar1.
 */
trait DRBG extends SecureRandomNumberGenerationAlgorithm:
  def algorithm: String = "DRBG"
end DRBG
object DRBG extends DRBG
