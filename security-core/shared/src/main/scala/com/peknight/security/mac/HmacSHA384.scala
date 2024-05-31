package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-384`}

trait HmacSHA384 extends HmacSHA2:
  val digest: MessageDigestAlgorithm = `SHA-384`
end HmacSHA384
object HmacSHA384 extends HmacSHA384
