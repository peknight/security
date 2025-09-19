package com.peknight.security.signature

/**
 * Keys for the Module-Lattice-Based Digital Signature Algorithm (ML-DSA)
 * using the ML-DSA-44 parameter set as defined in FIPS 204.
 */
trait `ML-DSA-44` extends `ML-DSA-N`:
  def parameter: Int = 44
end `ML-DSA-44`
object `ML-DSA-44` extends `ML-DSA-44`
