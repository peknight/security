package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-256`}

trait HmacPBESHA256 extends HmacPBESHA2:
  def digest: MessageDigestAlgorithm = `SHA-256`
end HmacPBESHA256
object HmacPBESHA256 extends HmacPBESHA256

