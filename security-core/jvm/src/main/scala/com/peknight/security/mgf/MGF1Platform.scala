package com.peknight.security.mgf

import com.peknight.security.digest.MessageDigestAlgorithm
import com.peknight.security.spec.MGF1ParameterSpec

import java.security.spec.AlgorithmParameterSpec

trait MGF1Platform extends MGFPlatform:
  def toMGFParameterSpec(messageDigest: MessageDigestAlgorithm): AlgorithmParameterSpec = MGF1ParameterSpec(messageDigest)
end MGF1Platform
