package com.peknight.security.random

/**
 * Obtains random numbers from the underlying native OS, blocking if necessary. For example, /dev/random on UNIX-like
 * systems.
 */
trait NativePRNGBlocking extends NativePRNG:
  override val algorithm: String = "NativePRNGBlocking"
end NativePRNGBlocking
object NativePRNGBlocking extends NativePRNGBlocking
