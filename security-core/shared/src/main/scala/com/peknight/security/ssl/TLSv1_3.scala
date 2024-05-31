package com.peknight.security.ssl

trait TLSv1_3 extends TLSv1:
  override val algorithm: String = "TLSv1.3"
end TLSv1_3
object TLSv1_3 extends TLSv1_3
