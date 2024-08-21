package com.peknight.security.signature

/**
 * Edwards-Curve signature algorithm with Ed25519
 */
trait Ed25519 extends EdDSA:
  override val keyByteLength: Int = 32
  override val signatureByteLength: Int = 64
  override val algorithm: String = "Ed25519"
end Ed25519
object Ed25519 extends Ed25519
