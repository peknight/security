package com.peknight.security.cipher.padding

import com.peknight.security.digest.Digest
import com.peknight.security.mgf.MGF
import com.peknight.security.parameter.AlgorithmParametersAlgorithm

trait OAEP
object OAEP extends OAEP with AlgorithmParametersAlgorithm:
  val algorithm: String = "OAEP"
  def withDigestAndMGF(digest: Digest, mgf: MGF): OAEPWithDigestAndMGFPadding = OAEPWithDigestAndMGFPadding(digest, mgf)
end OAEP
