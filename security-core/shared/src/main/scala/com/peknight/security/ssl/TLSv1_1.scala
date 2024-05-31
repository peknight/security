package com.peknight.security.ssl

trait TLSv1_1 extends TLSv1:
  override val algorithm: String = "TLSv1.1"
end TLSv1_1
object TLSv1_1 extends TLSv1_1
