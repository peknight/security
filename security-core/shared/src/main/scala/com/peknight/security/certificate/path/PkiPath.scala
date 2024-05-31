package com.peknight.security.certificate.path

trait PkiPath extends CertPathEncoding:
  val certPathEncoding: String = "PkiPath"
end PkiPath
object PkiPath extends PkiPath
