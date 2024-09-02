package com.peknight.security.spec

import scodec.bits.ByteVector

import javax.crypto.spec.SecretKeySpec as JSecretKeySpec

object SecretKeySpec:
  def apply(key: ByteVector, algorithm: SecretKeySpecAlgorithm): JSecretKeySpec =
    apply(key, algorithm.algorithm)
  private[security] def apply(key: ByteVector, algorithm: String): JSecretKeySpec =
    new JSecretKeySpec(key.toArray, algorithm)
end SecretKeySpec
