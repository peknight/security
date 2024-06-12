package com.peknight.security.mac

import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-256`}
import com.peknight.security.oid.ObjectIdentifier

trait HmacSHA256 extends HmacSHA2:
  val digest: MessageDigestAlgorithm = `SHA-256`
  override def oid: Option[ObjectIdentifier] = Some(ObjectIdentifier.unsafeFromString("1.2.840.113549.2.9"))
end HmacSHA256
object HmacSHA256 extends HmacSHA256
