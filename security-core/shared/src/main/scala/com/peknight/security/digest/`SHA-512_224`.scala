package com.peknight.security.digest

trait `SHA-512_224` extends `SHA-512`:
  override val outputLength: Int = 224
end `SHA-512_224`
object `SHA-512_224` extends `SHA-512_224`
