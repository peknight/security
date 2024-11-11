package com.peknight.security.spec

import com.peknight.security.digest.MessageDigestAlgorithm
import com.peknight.security.mgf.MGF

import java.security.spec.AlgorithmParameterSpec
import javax.crypto.spec.{PSource, OAEPParameterSpec as JOAEPParameterSpec}

object OAEPParameterSpec:
  def apply(md: MessageDigestAlgorithm, mgf: MGF, mgfSpec: AlgorithmParameterSpec, pSrc: PSource): JOAEPParameterSpec =
    JOAEPParameterSpec(md.algorithm, mgf.mgf, mgfSpec, pSrc)
end OAEPParameterSpec
