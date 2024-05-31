package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-256`}

trait HmacSHA256 extends HmacSHA2:
  val digest: MessageDigestAlgorithm = `SHA-256`
end HmacSHA256
object HmacSHA256 extends HmacSHA256
