package com.peknight.security.bouncycastle.cert.jcajce

import com.peknight.security.provider.Provider
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter as JJcaX509CertificateConverter

import java.security.Provider as JProvider

object JcaX509CertificateConverter:
  def apply(provider: Option[Provider | JProvider] = None): JJcaX509CertificateConverter =
    val converter = new JJcaX509CertificateConverter()
    provider match
      case Some(provider: Provider) => converter.setProvider(provider.name)
      case Some(provider: JProvider) => converter.setProvider(provider)
      case _ => converter
end JcaX509CertificateConverter
