package com.peknight.security.ecc.sec

trait `P-256` extends secp256r1:
  override def parameterSpecName: String = "P-256"
end `P-256`
object `P-256` extends `P-256`
