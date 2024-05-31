package com.peknight.security.ssl

trait SSL extends SSLContextAlgorithm:
  def algorithm: String = "SSL"
end SSL
object SSL extends SSL