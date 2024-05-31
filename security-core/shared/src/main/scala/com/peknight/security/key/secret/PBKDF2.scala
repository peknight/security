package com.peknight.security.key.secret

import com.peknight.security.random.PRF

trait PBKDF2
object PBKDF2:
  def withPRF(prf: PRF): PBKDF2WithPRF = PBKDF2WithPRF(prf)
end PBKDF2
