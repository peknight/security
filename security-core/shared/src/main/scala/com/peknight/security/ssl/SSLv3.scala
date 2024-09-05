package com.peknight.security.ssl

trait SSLv3 extends SSL:
  override def algorithm: String = "SSLv3"
end SSLv3
object SSLv3 extends SSLv3
