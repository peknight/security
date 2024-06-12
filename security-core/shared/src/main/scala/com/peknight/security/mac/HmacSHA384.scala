package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-384`}
import com.peknight.security.oid.ObjectIdentifier

trait HmacSHA384 extends HmacSHA2:
  val digest: MessageDigestAlgorithm = `SHA-384`
  override def oid: Option[ObjectIdentifier] = Some(ObjectIdentifier.unsafeFromString("1.2.840.113549.2.10"))
end HmacSHA384
object HmacSHA384 extends HmacSHA384
