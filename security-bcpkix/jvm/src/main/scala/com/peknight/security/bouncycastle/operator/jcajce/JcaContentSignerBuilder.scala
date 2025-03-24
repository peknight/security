package com.peknight.security.bouncycastle.operator.jcajce

import com.peknight.security.signature.SignatureAlgorithm
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder as JJcaContentSignerBuilder

object JcaContentSignerBuilder:
  def apply(signatureAlgorithm: SignatureAlgorithm): JJcaContentSignerBuilder =
    new JJcaContentSignerBuilder(signatureAlgorithm.algorithm)
end JcaContentSignerBuilder
