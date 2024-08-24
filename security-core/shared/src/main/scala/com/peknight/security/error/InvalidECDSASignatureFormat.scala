package com.peknight.security.error

trait InvalidECDSASignatureFormat extends InvalidSignature:
  override protected def lowPriorityMessage: Option[String] = Some("Invalid format of ECDSA signature")
end InvalidECDSASignatureFormat
object InvalidECDSASignatureFormat extends InvalidECDSASignatureFormat
