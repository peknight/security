package com.peknight.security.cipher.mode

import com.peknight.security.parameter.AlgorithmParametersAlgorithm

/**
 * Galois/Counter Mode
 */
trait GCM extends CipherAlgorithmMode with AlgorithmParametersAlgorithm:
  val mode: String = "GCM"
  val algorithm: String = mode
end GCM
object GCM extends GCM
