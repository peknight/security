package com.peknight.security.signature

import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm
import com.peknight.security.parameter.AlgorithmParametersAlgorithm
import com.peknight.security.signature.padding.{PSS, SignaturePadding}

trait `RSASSA-PSS` extends SignatureAlgorithm
  with AlgorithmParametersAlgorithm
  with KeyFactoryAlgorithm
  with KeyPairGeneratorAlgorithm
  with RSASSA:
  val padding: SignaturePadding = PSS
end `RSASSA-PSS`
object `RSASSA-PSS` extends `RSASSA-PSS`