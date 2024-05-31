package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-224`}

trait HmacPBESHA224 extends HmacPBESHA2:
  def digest: MessageDigestAlgorithm = `SHA-224`
end HmacPBESHA224
object HmacPBESHA224 extends HmacPBESHA224

