package com.peknight.security.random

/**
 * The name of the pseudo-random number generation (PRNG) algorithm supplied by the SUN provider.
 * This algorithm uses SHA-1 as the foundation of the PRNG.
 * It computes the SHA-1 hash over a true-random seed value concatenated with a 64-bit counter which is incremented
 * by 1 for each operation.
 * From the 160-bit SHA-1 output, only 64 bits are used.
 */
trait SHA1PRNG extends SecureRandomNumberGenerationAlgorithm:
  def algorithm: String = "SHA1PRNG"
end SHA1PRNG
object SHA1PRNG extends SHA1PRNG
