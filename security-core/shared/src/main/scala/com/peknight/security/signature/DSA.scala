package com.peknight.security.signature

import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm
import com.peknight.security.parameter.AlgorithmParametersAlgorithm
import com.peknight.security.parameter.generator.AlgorithmParameterGeneratorAlgorithm

/**
 * Digital Signature Algorithm
 */
trait DSA extends SSA
object DSA extends DSA
  with AlgorithmParameterGeneratorAlgorithm
  with AlgorithmParametersAlgorithm
  with KeyFactoryAlgorithm
  with KeyPairGeneratorAlgorithm:
  def algorithm: String = "DSA"
end DSA

