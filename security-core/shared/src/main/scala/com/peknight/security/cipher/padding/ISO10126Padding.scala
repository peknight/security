package com.peknight.security.cipher.padding

/**
 * This padding for block ciphers is described in the ISO 10126 standard (now withdrawn).
 */
trait ISO10126Padding extends CipherAlgorithmPadding:
  val padding: String = "ISO10126Padding"
end ISO10126Padding
object ISO10126Padding extends ISO10126Padding
