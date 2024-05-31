package com.peknight.security.ssl

trait TLSv1_2 extends TLSv1:
  override val algorithm: String = "TLSv1.2"
end TLSv1_2
object TLSv1_2 extends TLSv1_2
