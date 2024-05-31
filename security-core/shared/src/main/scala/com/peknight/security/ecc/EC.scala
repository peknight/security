package com.peknight.security.ecc

import com.peknight.security.cipher.Asymmetric
import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm
import com.peknight.security.parameter.AlgorithmParametersAlgorithm

/**
 * Elliptic Curve
 */
trait EC extends AlgorithmParametersAlgorithm with KeyFactoryAlgorithm with KeyPairGeneratorAlgorithm with Asymmetric:
  def algorithm: String = "EC"
end EC
object EC extends EC
