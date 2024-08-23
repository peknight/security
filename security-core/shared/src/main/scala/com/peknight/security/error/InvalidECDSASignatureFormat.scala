package com.peknight.security.error

trait InvalidECDSASignatureFormat extends SecurityError:
  override protected def lowPriorityMessage: Option[String] = Some("Invalid format of ECDSA signature")
end InvalidECDSASignatureFormat
object InvalidECDSASignatureFormat extends InvalidECDSASignatureFormat
