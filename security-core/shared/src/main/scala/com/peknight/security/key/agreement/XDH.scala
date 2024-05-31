package com.peknight.security.key.agreement

import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm

/**
 * Diffie-Hellman key agreement with elliptic curves as defined in RFC 7748.
 */
trait XDH extends DiffieHellman with KeyFactoryAlgorithm with KeyPairGeneratorAlgorithm:
  override def algorithm: String = "XDH"
end XDH
object XDH extends XDH
