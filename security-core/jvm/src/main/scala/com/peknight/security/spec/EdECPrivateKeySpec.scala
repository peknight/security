package com.peknight.security.spec

import scodec.bits.ByteVector

import java.security.spec.{NamedParameterSpec, EdECPrivateKeySpec as JEdECPrivateKeySpec}

object EdECPrivateKeySpec:
  def apply(params: NamedParameterSpec, bytes: ByteVector): JEdECPrivateKeySpec =
    new JEdECPrivateKeySpec(params, bytes.toArray)
end EdECPrivateKeySpec
