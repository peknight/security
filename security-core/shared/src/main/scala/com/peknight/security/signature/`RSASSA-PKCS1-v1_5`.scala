package com.peknight.security.signature

import com.peknight.security.padding.{Padding, `PKCS1-v1_5`}

trait `RSASSA-PKCS1-v1_5` extends RSASSA:
  val padding: Padding = `PKCS1-v1_5`
end `RSASSA-PKCS1-v1_5`
object `RSASSA-PKCS1-v1_5` extends `RSASSA-PKCS1-v1_5`
