package com.peknight.security.ssl

trait TLSv1 extends TLS:
  override def algorithm: String = "TLSv1"
end TLSv1
object TLSv1 extends TLSv1
