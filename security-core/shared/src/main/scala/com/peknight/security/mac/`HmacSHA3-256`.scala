package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA3-256`}

trait `HmacSHA3-256` extends HmacSHA3:
  val digest: MessageDigestAlgorithm = `SHA3-256`
end `HmacSHA3-256`
object `HmacSHA3-256` extends `HmacSHA3-256`
