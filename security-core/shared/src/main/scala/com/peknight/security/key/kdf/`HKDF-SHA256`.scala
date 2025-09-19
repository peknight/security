package com.peknight.security.key.kdf

import com.peknight.security.mac.{Hmac, HmacSHA256}

trait `HKDF-SHA256` extends HmacBasedKDF:
  val mac: Hmac = HmacSHA256
end `HKDF-SHA256`
object `HKDF-SHA256` extends `HKDF-SHA256`