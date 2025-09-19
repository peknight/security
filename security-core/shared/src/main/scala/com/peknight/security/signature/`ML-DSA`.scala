package com.peknight.security.signature

import com.peknight.security.key.asymmetric.AsymmetricKeyAlgorithm
import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm

/**
 * The Module-Lattice-Based Digital Signature Algorithm (ML-DSA) as defined in FIPS 204.
 * Keys for the Module-Lattice-Based Digital Signature Algorithm (ML-DSA) as defined in FIPS 204.
 * This algorithm supports keys with ML-DSA-44, ML-DSA-65, and ML-DSA-87 parameter sets.
 */
trait `ML-DSA` extends DSA
  with KeyFactoryAlgorithm
  with KeyPairGeneratorAlgorithm
  with SignatureAlgorithm:
  def algorithm: String = "ML-DSA"
end `ML-DSA`
object `ML-DSA` extends `ML-DSA` with AsymmetricKeyAlgorithm
