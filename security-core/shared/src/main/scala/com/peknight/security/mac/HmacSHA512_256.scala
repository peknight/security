package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-512_256`}

trait HmacSHA512_256 extends HmacSHA2:
  val digest: MessageDigestAlgorithm = `SHA-512_256`
end HmacSHA512_256
object HmacSHA512_256 extends HmacSHA512_256
