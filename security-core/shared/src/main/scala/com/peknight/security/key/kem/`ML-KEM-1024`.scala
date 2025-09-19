package com.peknight.security.key.kem

import com.peknight.security.key.factory.KeyFactoryAlgorithm

/**
 * The Module-Lattice-Based Key-Encapsulation Mechanism (ML-KEM)
 * using the ML-KEM-1024 parameter set as defined in FIPS 203.
 */
trait `ML-KEM-1024` extends `ML-KEM-N`:
  def parameter: Int = 1024
end `ML-KEM-1024`
object `ML-KEM-1024` extends `ML-KEM-1024`
