package com.peknight.security.signature

import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm
import com.peknight.security.padding.{PSS, Padding}
import com.peknight.security.parameter.AlgorithmParametersAlgorithm

trait `RSASSA-PSS` extends SignatureAlgorithm
  with AlgorithmParametersAlgorithm
  with KeyFactoryAlgorithm
  with KeyPairGeneratorAlgorithm
  with RSASSA:
  val padding: Padding = PSS
end `RSASSA-PSS`
object `RSASSA-PSS` extends `RSASSA-PSS`