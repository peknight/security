package com.peknight.security.digest

trait SHAKE extends MessageDigestAlgorithm:
  def algorithm: String = s"SHAKE$bitLength-$outputLength"
end SHAKE
