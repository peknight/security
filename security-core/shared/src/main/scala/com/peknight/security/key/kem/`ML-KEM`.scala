package com.peknight.security.key.kem

import com.peknight.security.key.asymmetric.AsymmetricKeyAlgorithm
import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm

/**
 * The Module-Lattice-Based Key-Encapsulation Mechanism (ML-KEM) as defined in FIPS 203.
 * This algorithm supports keys with ML-KEM-512, ML-KEM-768, and ML-KEM-1024 parameter sets.
 */
trait `ML-KEM` extends KEMAlgorithm
  with KeyFactoryAlgorithm
  with KeyPairGeneratorAlgorithm:
  def algorithm: String = "ML-KEM"
end `ML-KEM`
object `ML-KEM` extends `ML-KEM` with AsymmetricKeyAlgorithm
