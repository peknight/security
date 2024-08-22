package com.peknight.security.signature

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-1`}

/**
 * Elliptic Curve Digital Signature Algorithm
 */
trait ECDSA extends SignatureAlgorithm with DSA:
  def digest: MessageDigestAlgorithm
  def signature: DigestWithEncryption = digest.withEncryption(ECDSA)
  override def algorithm: String = "ECDSA"
object ECDSA extends ECDSA:
  def digest: MessageDigestAlgorithm = `SHA-1`
  private case class ECDSA(digest: MessageDigestAlgorithm) extends com.peknight.security.signature.ECDSA
  def apply(digest: MessageDigestAlgorithm): com.peknight.security.signature.ECDSA = ECDSA(digest)
end ECDSA
