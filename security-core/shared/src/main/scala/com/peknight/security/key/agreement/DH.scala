package com.peknight.security.key.agreement

import com.peknight.security.key.asymmetric.AsymmetricKeyAlgorithm

trait DH extends DiffieHellman with AsymmetricKeyAlgorithm:
  override def algorithm: String = "DH"
end DH
object DH extends DH
