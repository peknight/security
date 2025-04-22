package com.peknight.security.bouncycastle.operator.jcajce

import com.peknight.security.provider.Provider
import com.peknight.security.signature.SignatureAlgorithm
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder as JJcaContentSignerBuilder

import java.security.Provider as JProvider

object JcaContentSignerBuilder:
  def apply(signatureAlgorithm: SignatureAlgorithm, provider: Option[Provider | JProvider] = None)
  : JJcaContentSignerBuilder =
    val builder = new JJcaContentSignerBuilder(signatureAlgorithm.algorithm)
    provider match
      case Some(provider: Provider) => builder.setProvider(provider.name)
      case Some(provider: JProvider) => builder.setProvider(provider)
      case _ => builder
end JcaContentSignerBuilder
