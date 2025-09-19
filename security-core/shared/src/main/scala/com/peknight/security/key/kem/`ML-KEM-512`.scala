package com.peknight.security.key.kem

import com.peknight.security.key.factory.KeyFactoryAlgorithm

/**
 * The Module-Lattice-Based Key-Encapsulation Mechanism (ML-KEM)
 * using the ML-KEM-512 parameter set as defined in FIPS 203.
 */
trait `ML-KEM-512` extends `ML-KEM-N`:
  def parameter: Int = 512
end `ML-KEM-512`
object `ML-KEM-512` extends `ML-KEM-512`
