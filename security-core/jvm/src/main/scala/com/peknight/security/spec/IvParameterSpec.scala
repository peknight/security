package com.peknight.security.spec

import scodec.bits.ByteVector

import javax.crypto.spec.IvParameterSpec as JIvParameterSpec

object IvParameterSpec:
  def apply(iv: ByteVector): JIvParameterSpec = JIvParameterSpec(iv.toArray)
end IvParameterSpec
