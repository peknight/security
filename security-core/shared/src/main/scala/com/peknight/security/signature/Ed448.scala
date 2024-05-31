package com.peknight.security.signature

import com.peknight.security.spec.NamedParameterSpecName

/**
 * Edwards-Curve signature algorithm with Ed448
 */
trait Ed448 extends EdDSA with NamedParameterSpecName:
  override val algorithm: String = "Ed448"
  def parameterSpecName: String = algorithm
end Ed448
object Ed448 extends Ed448
