package com.peknight.security.bouncycastle.operator.jcajce

import com.peknight.security.provider.Provider
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder as JJcaContentVerifierProviderBuilder

import java.security.Provider as JProvider

object JcaContentVerifierProviderBuilder:
  def apply(provider: Option[Provider | JProvider] = None): JJcaContentVerifierProviderBuilder =
    val builder = new JJcaContentVerifierProviderBuilder
    provider match
      case Some(provider: Provider) => builder.setProvider(provider.name)
      case Some(provider: JProvider) => builder.setProvider(provider)
      case _ => builder
end JcaContentVerifierProviderBuilder
