package com.peknight.security.ecc.sec

trait `P-384` extends secp384r1:
  override def parameterSpecName: String = "P-384"
end `P-384`
object `P-384` extends `P-384`
