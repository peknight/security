package com.peknight.security.cipher

import com.peknight.security.padding.{Padding, `PKCS1-v1_5`}

trait `RSAES-PKCS1-v1_5` extends RSAES:
  def padding: Padding = `PKCS1-v1_5`
end `RSAES-PKCS1-v1_5`
object `RSAES-PKCS1-v1_5` extends `RSAES-PKCS1-v1_5`
