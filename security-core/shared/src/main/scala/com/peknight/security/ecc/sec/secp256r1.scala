package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Prime
import com.peknight.security.oid.ObjectIdentifier

trait secp256r1 extends StandardsForEfficientCryptography with secp256r1Platform with Prime with Random:
  def bitLength: Int = 256
  def curveOrder: Int = 1
  override def oid: Option[ObjectIdentifier] = Some(ObjectIdentifier.unsafeFromString("1.2.840.10045.3.1"))
end secp256r1
object secp256r1 extends secp256r1
