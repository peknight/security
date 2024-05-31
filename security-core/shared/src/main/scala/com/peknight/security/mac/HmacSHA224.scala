package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-224`}

trait HmacSHA224 extends HmacSHA2:
  val digest: MessageDigestAlgorithm = `SHA-224`
end HmacSHA224
object HmacSHA224 extends HmacSHA224
