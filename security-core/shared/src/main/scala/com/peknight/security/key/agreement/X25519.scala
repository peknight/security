package com.peknight.security.key.agreement

/**
 * Diffie-Hellman key agreement with Curve25519 as defined in RFC 7748.
 */
trait X25519 extends XDH:
  override val bits: Int = 255
  override val keyByteLength: Int = 32
  override val prime: BigInt = BigInt("57896044618658097711785492504343953926634992332820282019728792003956564819949")
  override def algorithm: String = "X25519"
end X25519
object X25519 extends X25519:
  override val algorithm: String = "X25519"
end X25519
