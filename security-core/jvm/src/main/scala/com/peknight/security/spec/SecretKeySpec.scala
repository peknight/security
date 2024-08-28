package com.peknight.security.spec

import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm
import scodec.bits.ByteVector

import javax.crypto.spec.SecretKeySpec as JSecretKeySpec

object SecretKeySpec:
  def apply(key: ByteVector, algorithm: SecretKeyFactoryAlgorithm): JSecretKeySpec =
    apply(key, algorithm.algorithm)
  private[security] def apply(key: ByteVector, algorithm: String): JSecretKeySpec =
    new JSecretKeySpec(key.toArray, algorithm)
end SecretKeySpec
