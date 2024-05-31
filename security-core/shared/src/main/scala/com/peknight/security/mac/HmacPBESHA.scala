package com.peknight.security.mac

trait HmacPBESHA extends Hmac:
  override def algorithm: String = s"HmacPBE${digest.abbreviation}"
end HmacPBESHA
