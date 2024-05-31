package com.peknight.security.key.agreement

import com.peknight.security.spec.NamedParameterSpecName

/**
 * Diffie-Hellman key agreement with Curve448 as defined in RFC 7748.
 */
trait X448 extends XDH with NamedParameterSpecName:
  override val algorithm: String = "X448"
  def parameterSpecName: String = algorithm
end X448
object X448 extends X448
