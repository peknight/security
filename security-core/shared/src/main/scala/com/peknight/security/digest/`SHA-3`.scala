package com.peknight.security.digest

trait `SHA-3` extends SHA:
  def algorithm: String = s"SHA3-$bitLength"
end `SHA-3`
