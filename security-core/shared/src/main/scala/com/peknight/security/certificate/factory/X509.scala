package com.peknight.security.certificate.factory

import com.peknight.security.key.encoding.KeyEncoding

/**
 * The certificate type defined in X.509, also specified in RFC 5280.
 */
object X509 extends CertificateFactoryType with KeyEncoding with X509Platform:
  val certFactoryType: String = "X.509"
  val keyEncoding: String = certFactoryType
end X509
