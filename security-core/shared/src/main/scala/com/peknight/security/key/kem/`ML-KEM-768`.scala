package com.peknight.security.key.kem

import com.peknight.security.key.factory.KeyFactoryAlgorithm

/**
 * The Module-Lattice-Based Key-Encapsulation Mechanism (ML-KEM)
 * using the ML-KEM-768 parameter set as defined in FIPS 203.
 */
trait `ML-KEM-768` extends `ML-KEM-N`:
  def parameter: Int = 768
end `ML-KEM-768`
object `ML-KEM-768` extends `ML-KEM-768`
