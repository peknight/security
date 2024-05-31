package com.peknight.security.ssl

trait SSLv2 extends SSL:
  override val algorithm: String = "SSLv2"
end SSLv2
object SSLv2 extends SSLv2
