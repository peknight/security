package com.peknight.security.key.agreement

import com.peknight.security.spec.NamedParameterSpecName

/**
 * Diffie-Hellman key agreement with Curve25519 as defined in RFC 7748.
 */
trait X25519 extends XDH with NamedParameterSpecName:
  override val algorithm: String = "X25519"
  def parameterSpecName: String = algorithm
end X25519
object X25519 extends X25519
