package com.peknight.security.spec

import scodec.bits.ByteVector

import javax.crypto.spec.GCMParameterSpec as JGCMParameterSpec

object GCMParameterSpec:
  def apply(tLen: Int, src: ByteVector): JGCMParameterSpec =
    new JGCMParameterSpec(tLen, src.toArray)
end GCMParameterSpec
