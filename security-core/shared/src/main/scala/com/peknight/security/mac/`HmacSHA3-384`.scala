package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA3-384`}

trait `HmacSHA3-384` extends HmacSHA3:
  val digest: MessageDigestAlgorithm = `SHA3-384`
end `HmacSHA3-384`
object `HmacSHA3-384` extends `HmacSHA3-384`
