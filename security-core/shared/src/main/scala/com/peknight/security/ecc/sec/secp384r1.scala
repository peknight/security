package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Prime
import com.peknight.security.oid.ObjectIdentifier

trait secp384r1 extends StandardsForEfficientCryptography with Prime with Random:
  def bitLength: Int = 384
  def curveOrder: Int = 1
  override def oid: Option[ObjectIdentifier] = Some(ObjectIdentifier.unsafeFromString("1.3.132.0.34"))
end secp384r1
object secp384r1 extends secp384r1
