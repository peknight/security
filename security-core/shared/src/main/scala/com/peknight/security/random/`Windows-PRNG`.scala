package com.peknight.security.random

/**
 * Obtains random numbers from the underlying Windows OS.
 */
trait `Windows-PRNG` extends SecureRandomNumberGenerationAlgorithm:
  def algorithm: String = "Windows-PRNG"
end `Windows-PRNG`
object `Windows-PRNG` extends `Windows-PRNG`
