package com.peknight.security.mgf

import com.peknight.security.digest.MessageDigest

import java.security.spec.AlgorithmParameterSpec

trait MGFPlatform:
  def toMGFParameterSpec(messageDigest: MessageDigest): AlgorithmParameterSpec
end MGFPlatform
