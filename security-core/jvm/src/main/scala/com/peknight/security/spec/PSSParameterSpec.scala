package com.peknight.security.spec

import com.peknight.security.digest.MessageDigestAlgorithm
import com.peknight.security.mgf.MGF

import java.security.spec.{AlgorithmParameterSpec, PSSParameterSpec as JPSSParameterSpec}

object PSSParameterSpec:
  def apply(md: MessageDigestAlgorithm, mgf: MGF, mgfSpec: AlgorithmParameterSpec, saltLen: Int,
            trailerField: Int = JPSSParameterSpec.TRAILER_FIELD_BC)
  : JPSSParameterSpec =
    new JPSSParameterSpec(md.algorithm, mgf.mgf, mgfSpec, saltLen, trailerField)
end PSSParameterSpec
