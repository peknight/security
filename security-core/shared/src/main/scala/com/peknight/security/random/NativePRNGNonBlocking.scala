package com.peknight.security.random

/**
 * 	Obtains random numbers from the underlying native OS, without blocking to prevent applications from excessive
 * 	stalling. For example, /dev/urandom on UNIX-like systems.
 */
trait NativePRNGNonBlocking extends NativePRNG:
  override def algorithm: String = "NativePRNGNonBlocking"
end NativePRNGNonBlocking
object NativePRNGNonBlocking extends NativePRNGNonBlocking
