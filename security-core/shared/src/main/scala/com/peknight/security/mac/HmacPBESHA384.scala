package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-384`}

trait HmacPBESHA384 extends HmacPBESHA2:
  def digest: MessageDigestAlgorithm = `SHA-384`
end HmacPBESHA384
object HmacPBESHA384 extends HmacPBESHA384

