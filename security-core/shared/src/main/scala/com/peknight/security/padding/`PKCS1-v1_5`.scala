package com.peknight.security.padding

trait `PKCS1-v1_5` extends Padding:
  def padding: String = "PKCS1-v1_5"
end `PKCS1-v1_5`
object `PKCS1-v1_5` extends `PKCS1-v1_5`
