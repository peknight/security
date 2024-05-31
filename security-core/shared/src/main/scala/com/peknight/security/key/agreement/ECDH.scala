package com.peknight.security.key.agreement

/**
 * Elliptic Curve Diffie-Hellman as defined in ANSI X9.63
 */
trait ECDH extends DiffieHellman:
  override def algorithm: String = "ECDH"
object ECDH extends ECDH
