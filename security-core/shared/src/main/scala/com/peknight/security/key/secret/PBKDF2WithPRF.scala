package com.peknight.security.key.secret

import com.peknight.security.random.PRF

trait PBKDF2WithPRF extends SecretKeyAlgorithm with SecretKeyFactoryAlgorithm:
  def prf: PRF
  override def algorithm: String = s"PBKDF2With${prf.prf}"
end PBKDF2WithPRF
object PBKDF2WithPRF:
  private case class PBKDFWithPRF(prf: PRF) extends com.peknight.security.key.secret.PBKDF2WithPRF
  def apply(prf: PRF): com.peknight.security.key.secret.PBKDF2WithPRF = PBKDFWithPRF(prf)
end PBKDF2WithPRF
