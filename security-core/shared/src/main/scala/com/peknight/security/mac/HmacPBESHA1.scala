package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-1`}

trait HmacPBESHA1 extends HmacPBESHA:
  val digest: MessageDigestAlgorithm = `SHA-1`
end HmacPBESHA1
object HmacPBESHA1 extends HmacPBESHA1
