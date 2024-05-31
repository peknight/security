package com.peknight.security.signature

import com.peknight.security.algorithm.Algorithm
import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-1`}
import com.peknight.security.format.Format
import com.peknight.security.mgf.MGF

/**
 * Elliptic Curve Digital Signature Algorithm
 */
trait ECDSA extends DigestWithEncryption with DSA:
  def digest: MessageDigestAlgorithm = `SHA-1`
  def encryption: Algorithm = this
  def mgf: Option[MGF] = None
  def format: Option[Format] = None
  override def algorithm: String = "ECDSA"
object ECDSA extends ECDSA
