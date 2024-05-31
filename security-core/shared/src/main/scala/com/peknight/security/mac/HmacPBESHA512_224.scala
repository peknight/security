package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-512_224`}

trait HmacPBESHA512_224 extends HmacPBESHA2:
  def digest: MessageDigestAlgorithm = `SHA-512_224`
end HmacPBESHA512_224
object HmacPBESHA512_224 extends HmacPBESHA512_224

