package com.peknight.security.key.kdf

import com.peknight.security.mac.{Hmac, HmacSHA512}

trait `HKDF-SHA512` extends HmacBasedKDF:
  val mac: Hmac = HmacSHA512
end `HKDF-SHA512`
object `HKDF-SHA512` extends `HKDF-SHA512`