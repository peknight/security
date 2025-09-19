package com.peknight.security.ecc

import com.peknight.security.key.asymmetric.AsymmetricKeyAlgorithm
import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm
import com.peknight.security.parameter.AlgorithmParametersAlgorithm

/**
 * Elliptic Curve
 */
trait EC extends AsymmetricKeyAlgorithm
  with AlgorithmParametersAlgorithm
  with KeyFactoryAlgorithm
  with KeyPairGeneratorAlgorithm:
  def algorithm: String = "EC"
end EC
object EC extends EC with ECCompanion
