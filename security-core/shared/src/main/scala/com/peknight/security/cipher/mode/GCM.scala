package com.peknight.security.cipher.mode

import com.peknight.security.parameter.AlgorithmParametersAlgorithm

/**
 * Galois/Counter Mode
 */
trait GCM extends CipherAlgorithmMode with AlgorithmParametersAlgorithm:
  def mode: String = "GCM"
  def algorithm: String = mode
end GCM
object GCM extends GCM
