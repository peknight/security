package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-512_256`}

trait HmacPBESHA512_256 extends HmacPBESHA2:
  def digest: MessageDigestAlgorithm = `SHA-512_256`
end HmacPBESHA512_256
object HmacPBESHA512_256 extends HmacPBESHA512_256

