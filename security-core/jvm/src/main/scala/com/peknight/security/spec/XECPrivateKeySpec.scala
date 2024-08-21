package com.peknight.security.spec

import scodec.bits.ByteVector

import java.security.spec.{AlgorithmParameterSpec, XECPrivateKeySpec as JXECPrivateKeySpec}

object XECPrivateKeySpec:
  def apply(params: AlgorithmParameterSpec, scalar: ByteVector): JXECPrivateKeySpec =
    new JXECPrivateKeySpec(params, scalar.toArray)
end XECPrivateKeySpec
