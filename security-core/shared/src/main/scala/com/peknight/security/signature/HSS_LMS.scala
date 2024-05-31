package com.peknight.security.signature

import com.peknight.security.key.factory.KeyFactoryAlgorithm

/**
 * Leighton-Micali Signature (LMS) system with the Hierarchical Signature System (HSS)
 */
trait HSS_LMS extends HSS with SignatureAlgorithm with KeyFactoryAlgorithm:
  override val algorithm: String = "HSS/LMS"
end HSS_LMS
object HSS_LMS extends HSS_LMS
