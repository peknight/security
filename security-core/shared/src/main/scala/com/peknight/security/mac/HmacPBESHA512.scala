package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-512`}

trait HmacPBESHA512 extends HmacPBESHA2:
  def digest: MessageDigestAlgorithm = `SHA-512`
end HmacPBESHA512
object HmacPBESHA512 extends HmacPBESHA512

