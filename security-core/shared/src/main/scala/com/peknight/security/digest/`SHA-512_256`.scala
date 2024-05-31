package com.peknight.security.digest

trait `SHA-512_256` extends `SHA-512`:
  override val outputLength: Int = 256
end `SHA-512_256`
object `SHA-512_256` extends `SHA-512_256`
