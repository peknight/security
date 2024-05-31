package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-512`}

trait HmacSHA512 extends HmacSHA2:
  val digest: MessageDigestAlgorithm = `SHA-512`
end HmacSHA512
object HmacSHA512 extends HmacSHA512
