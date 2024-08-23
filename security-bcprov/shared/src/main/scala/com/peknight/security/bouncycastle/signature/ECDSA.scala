package com.peknight.security.bouncycastle.signature

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-1`}
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm

trait ECDSA extends com.peknight.security.signature.ECDSA with KeyPairGeneratorAlgorithm
object ECDSA extends ECDSA:
  def digest: MessageDigestAlgorithm = `SHA-1`
end ECDSA
