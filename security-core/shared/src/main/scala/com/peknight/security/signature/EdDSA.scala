package com.peknight.security.signature

import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm

/**
 * Edwards-Curve signature algorithm with elliptic curves
 */
trait EdDSA extends DSA
  with SignatureAlgorithm
  with KeyFactoryAlgorithm
  with KeyPairGeneratorAlgorithm:
  override def algorithm: String = "EdDSA"
end EdDSA
object EdDSA extends EdDSA
