package com.peknight.security.key.secret

/**
 * This algorithm defines a general-purpose secret key.
 * This key is not intended to be used for cryptographic operations;
 * rather it is typically used as input keying material for deriving other keys.
 * This algorithm is also used to represent PKCS #11 generic secret key objects (key type CKK_GENERIC_SECRET_TYPE).
 */
trait Generic extends SecretKeyAlgorithm with SecretKeyFactoryAlgorithm:
  def algorithm: String = "Generic"
end Generic
object Generic extends Generic
