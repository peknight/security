package com.peknight.security.key.agreement

import com.peknight.security.cipher.Asymmetric
import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm
import com.peknight.security.parameter.AlgorithmParametersAlgorithm
import com.peknight.security.parameter.generator.AlgorithmParameterGeneratorAlgorithm

trait DiffieHellman extends KeyAgreementAlgorithm
  with Asymmetric:
  def algorithm: String = "DiffieHellman"
end DiffieHellman
object DiffieHellman extends DiffieHellman
  with AlgorithmParameterGeneratorAlgorithm
  with AlgorithmParametersAlgorithm
  with KeyFactoryAlgorithm
  with KeyPairGeneratorAlgorithm:
  override val abbreviation: String = "DH"
end DiffieHellman
