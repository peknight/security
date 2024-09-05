package com.peknight.security.ssl

trait DTLSv1_0 extends DTLS:
  override def algorithm: String = "DTLSv1.0"
end DTLSv1_0
object DTLSv1_0 extends DTLSv1_0
