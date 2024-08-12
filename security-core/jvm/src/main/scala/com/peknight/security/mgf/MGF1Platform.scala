package com.peknight.security.mgf
import com.peknight.security.digest.MessageDigest

import java.security.spec.{AlgorithmParameterSpec, MGF1ParameterSpec}

trait MGF1Platform extends MGFPlatform:
  def toMGFParameterSpec(messageDigest: MessageDigest): AlgorithmParameterSpec =
    new MGF1ParameterSpec(messageDigest.algorithm)
end MGF1Platform
