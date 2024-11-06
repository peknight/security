package com.peknight.security.key.secret

import com.peknight.security.random.PRF
import com.peknight.security.spec.SecretKeySpecAlgorithm

trait PBKDF2 extends SecretKeySpecAlgorithm:
  def algorithm: String = "PBKDF2"
end PBKDF2
object PBKDF2:
  def withPRF(prf: PRF): PBKDF2WithPRF = PBKDF2WithPRF(prf)
end PBKDF2
