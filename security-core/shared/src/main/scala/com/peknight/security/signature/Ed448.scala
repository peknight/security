package com.peknight.security.signature

/**
 * Edwards-Curve signature algorithm with Ed448
 */
trait Ed448 extends EdDSA:
  override val keyByteLength: Int = 57
  override def algorithm: String = "Ed448"
end Ed448
object Ed448 extends Ed448:
  override val algorithm: String = "Ed448"
end Ed448

