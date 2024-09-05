package com.peknight.security.key.agreement

/**
 * Diffie-Hellman key agreement with Curve448 as defined in RFC 7748.
 */
trait X448 extends XDH:
  override val bits: Int = 448
  override val keyByteLength: Int = 57
  override val prime: BigInt = BigInt("726838724295606890549323807888004534353641360687318060281490199180612328166730772686396383698676545930088884461843637361053498018365439")
  override def algorithm: String = "X448"
end X448
object X448 extends X448:
  override val algorithm: String = "X448"
end X448
