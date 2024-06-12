package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-512`}
import com.peknight.security.oid.ObjectIdentifier

trait HmacSHA512 extends HmacSHA2:
  val digest: MessageDigestAlgorithm = `SHA-512`
  override def oid: Option[ObjectIdentifier] = Some(ObjectIdentifier.unsafeFromString("1.2.840.113549.2.11"))
end HmacSHA512
object HmacSHA512 extends HmacSHA512
