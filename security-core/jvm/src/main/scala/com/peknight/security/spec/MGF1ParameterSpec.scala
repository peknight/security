package com.peknight.security.spec

import com.peknight.security.digest.MessageDigestAlgorithm

import java.security.spec.MGF1ParameterSpec as JMGF1ParameterSpec

object MGF1ParameterSpec:
  def apply(md: MessageDigestAlgorithm): JMGF1ParameterSpec = new JMGF1ParameterSpec(md.algorithm)
end MGF1ParameterSpec
