package com.peknight.security.ssl

trait TLS extends SSLContextAlgorithm:
  def algorithm: String = "TLS"
end TLS
object TLS extends TLS
