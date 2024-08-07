package com.peknight.security.spec

import com.peknight.security.key.factory.KeyFactoryAlgorithm
import scodec.bits.ByteVector

import java.security.spec.PKCS8EncodedKeySpec as JPKCS8EncodedKeySpec

object PKCS8EncodedKeySpec:
  def apply(encodedKey: ByteVector): JPKCS8EncodedKeySpec = new JPKCS8EncodedKeySpec(encodedKey.toArray)
  def apply(encodedKey: ByteVector, algorithm: KeyFactoryAlgorithm): JPKCS8EncodedKeySpec =
    new JPKCS8EncodedKeySpec(encodedKey.toArray, algorithm.algorithm)
end PKCS8EncodedKeySpec
