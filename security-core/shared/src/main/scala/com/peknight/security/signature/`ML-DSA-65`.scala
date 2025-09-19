package com.peknight.security.signature

/**
 * Keys for the Module-Lattice-Based Digital Signature Algorithm (ML-DSA)
 * using the ML-DSA-65 parameter set as defined in FIPS 204.
 */
trait `ML-DSA-65` extends `ML-DSA-N`:
  def parameter: Int = 65
end `ML-DSA-65`
object `ML-DSA-65` extends `ML-DSA-65`
