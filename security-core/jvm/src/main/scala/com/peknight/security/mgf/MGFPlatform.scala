package com.peknight.security.mgf

import com.peknight.security.digest.MessageDigestAlgorithm

import java.security.spec.AlgorithmParameterSpec

trait MGFPlatform:
  def toMGFParameterSpec(messageDigest: MessageDigestAlgorithm): AlgorithmParameterSpec
end MGFPlatform
