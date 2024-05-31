package com.peknight.security.ssl

import com.peknight.security.certificate.path.{CertPathBuilderAlgorithm, CertPathValidatorAlgorithm}
import com.peknight.security.key.manager.KeyManagerFactoryAlgorithm

trait PKIX extends TrustManagerFactoryAlgorithm
  with CertPathBuilderAlgorithm
  with CertPathValidatorAlgorithm
  with KeyManagerFactoryAlgorithm:
  def algorithm: String = "PKIX"
end PKIX
object PKIX extends PKIX
