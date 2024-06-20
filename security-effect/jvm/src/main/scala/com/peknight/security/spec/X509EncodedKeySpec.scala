package com.peknight.security.spec

import com.peknight.security.key.factory.KeyFactoryAlgorithm
import scodec.bits.ByteVector

import java.security.spec.X509EncodedKeySpec as JX509EncodedKeySpec

object X509EncodedKeySpec:
  def apply(encodedKey: ByteVector): JX509EncodedKeySpec = new JX509EncodedKeySpec(encodedKey.toArray)
  def apply(encodedKey: ByteVector, algorithm: KeyFactoryAlgorithm): JX509EncodedKeySpec =
    new JX509EncodedKeySpec(encodedKey.toArray, algorithm.algorithm)
end X509EncodedKeySpec
