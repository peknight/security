package com.peknight.security.digest

trait `SHAKE128-256` extends SHAKE:
  def bitLength: Int = 128
  override def outputLength: Int = 256
end `SHAKE128-256`
object `SHAKE128-256` extends `SHAKE128-256`
