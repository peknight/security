package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA3-512`}

trait `HmacSHA3-512` extends HmacSHA3:
  val digest: MessageDigestAlgorithm = `SHA3-512`
end `HmacSHA3-512`
object `HmacSHA3-512` extends `HmacSHA3-512`
