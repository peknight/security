package com.peknight.security.signature

/**
 * Edwards-Curve signature algorithm with Ed448
 */
trait Ed448 extends EdDSA:
  val keyByteLength: Int = 57
  val signatureByteLength: Int = 114
  override val algorithm: String = "Ed448"
end Ed448
object Ed448 extends Ed448
