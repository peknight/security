package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA3-224`}

trait `HmacSHA3-224` extends HmacSHA3:
  val digest: MessageDigestAlgorithm = `SHA3-224`
end `HmacSHA3-224`
object `HmacSHA3-224` extends `HmacSHA3-224`
