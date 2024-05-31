package com.peknight.security.cipher.mode

/**
 * A simplification of OFB, Counter mode updates the input block as a counter.
 */
trait CTR extends CipherAlgorithmMode:
  val mode: String = "CTR"
end CTR
object CTR extends CTR
