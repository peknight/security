package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-512_224`}

trait HmacSHA512_224 extends HmacSHA2:
  val digest: MessageDigestAlgorithm = `SHA-512_224`
end HmacSHA512_224
object HmacSHA512_224 extends HmacSHA512_224
