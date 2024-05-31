package com.peknight.security.ssl

trait DTLS extends SSLContextAlgorithm:
  def algorithm: String = "DTLS"
end DTLS
object DTLS extends DTLS
