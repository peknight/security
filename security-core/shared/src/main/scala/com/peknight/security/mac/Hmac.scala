package com.peknight.security.mac

import com.peknight.security.digest.MessageDigestAlgorithm
import com.peknight.security.random.PRF
import com.peknight.security.spec.SecretKeySpecAlgorithm

/**
 * Hash-based Message Authentication Code
 */
trait Hmac extends MACAlgorithm with PRF:
  def digest: MessageDigestAlgorithm
  def algorithm: String = s"Hmac${digest.abbreviation}"
  def prf: String = algorithm
end Hmac
object Hmac extends SecretKeySpecAlgorithm:
  override def algorithm: String = "HMAC"
end Hmac
