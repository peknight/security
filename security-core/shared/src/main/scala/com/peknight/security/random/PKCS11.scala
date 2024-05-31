package com.peknight.security.random

/**
 * Obtains random numbers from the underlying installed and configured PKCS #11 library.
 */
trait PKCS11 extends SecureRandomNumberGenerationAlgorithm:
  def algorithm: String = "PKCS11"
end PKCS11
object PKCS11 extends PKCS11
