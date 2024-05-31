package com.peknight.security.digest

trait `SHA-1` extends SHA:
  val bitLength: Int = 160
  val algorithm: String = "SHA-1"
  override val abbreviation: String = "SHA1"
end `SHA-1`
object `SHA-1` extends `SHA-1`
