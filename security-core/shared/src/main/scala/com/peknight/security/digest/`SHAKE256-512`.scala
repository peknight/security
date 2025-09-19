package com.peknight.security.digest

trait `SHAKE256-512` extends SHAKE:
  def bitLength: Int = 256
  override def outputLength: Int = 512
end `SHAKE256-512`
object `SHAKE256-512` extends `SHAKE256-512`
