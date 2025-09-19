package com.peknight.security.key.kdf

import com.peknight.security.mac.{Hmac, HmacSHA384}

trait `HKDF-SHA384` extends HmacBasedKDF:
  val mac: Hmac = HmacSHA384
end `HKDF-SHA384`
object `HKDF-SHA384` extends `HKDF-SHA384`