package com.peknight.security.ssl

trait DTLSv1_2 extends DTLS:
  override val algorithm: String = "DTLSv1.2"
end DTLSv1_2
object DTLSv1_2 extends DTLSv1_2
