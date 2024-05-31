package com.peknight.security.random

/**
 * Obtains random numbers from the underlying native OS. No assertions are made as to the blocking nature of generating
 * these numbers.
 */
trait NativePRNG extends SecureRandomNumberGenerationAlgorithm:
  def algorithm: String = "NativePRNG"
end NativePRNG
object NativePRNG extends NativePRNG
