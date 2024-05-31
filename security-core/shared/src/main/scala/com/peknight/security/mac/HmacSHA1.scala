package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-1`}

trait HmacSHA1 extends HmacSHA:
  def digest: MessageDigestAlgorithm = `SHA-1`
end HmacSHA1
object HmacSHA1 extends HmacSHA1
