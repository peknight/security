package com.peknight.security.bouncycastle.openssl.jcajce

import com.peknight.security.provider.Provider
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter as JJcaPEMKeyConverter

import java.security.Provider as JProvider

object JcaPEMKeyConverter:
  def apply(provider: Option[Provider | JProvider] = None): JJcaPEMKeyConverter =
    val converter = new JJcaPEMKeyConverter()
    provider match
      case Some(provider: Provider) => converter.setProvider(provider.name)
      case Some(provider: JProvider) => converter.setProvider(provider)
      case _ => converter
end JcaPEMKeyConverter
