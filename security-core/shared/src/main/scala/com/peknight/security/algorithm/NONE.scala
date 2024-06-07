package com.peknight.security.algorithm

import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.digest.MessageDigestAlgorithm

trait NONE extends MessageDigestAlgorithm with CipherAlgorithmMode:
  val bitLength: Int = 0
  def algorithm: String = "NONE"
  val mode: String = algorithm
end NONE
object NONE extends NONE
