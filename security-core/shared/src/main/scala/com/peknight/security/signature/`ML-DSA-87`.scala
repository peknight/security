package com.peknight.security.signature

/**
 * Keys for the Module-Lattice-Based Digital Signature Algorithm (ML-DSA)
 * using the ML-DSA-87 parameter set as defined in FIPS 204.
 */
trait `ML-DSA-87` extends `ML-DSA-N`:
  def parameter: Int = 87
end `ML-DSA-87`
object `ML-DSA-87` extends `ML-DSA-87`
