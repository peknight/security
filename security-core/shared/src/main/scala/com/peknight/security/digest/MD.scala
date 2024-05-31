package com.peknight.security.digest

/**
 * Message Digest
 */
trait MD extends MessageDigestAlgorithm:
  val bitLength: Int = 128
end MD
