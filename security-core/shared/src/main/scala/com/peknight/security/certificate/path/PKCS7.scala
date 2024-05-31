package com.peknight.security.certificate.path

trait PKCS7 extends CertPathEncoding:
  val certPathEncoding: String = "PKCS7"
end PKCS7
object PKCS7 extends PKCS7
