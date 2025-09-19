package com.peknight.security.signature

import com.peknight.security.key.asymmetric.AsymmetricKeyAlgorithm
import com.peknight.security.key.factory.KeyFactoryAlgorithm

/**
 * Leighton-Micali Signature (LMS) system with the Hierarchical Signature System (HSS)
 */
trait HSS_LMS extends HSS
  with AsymmetricKeyAlgorithm
  with SignatureAlgorithm
  with KeyFactoryAlgorithm:
  override def algorithm: String = "HSS/LMS"
end HSS_LMS
object HSS_LMS extends HSS_LMS
