package com.peknight.security.signature

import com.peknight.security.spec.NamedParameterSpecName

/**
 * Edwards-Curve signature algorithm with Ed25519
 */
trait Ed25519 extends EdDSA with NamedParameterSpecName:
  override val algorithm: String = "Ed25519"
  def parameterSpecName: String = algorithm
end Ed25519
object Ed25519 extends Ed25519
