package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Prime
import com.peknight.security.oid.ObjectIdentifier

trait secp521r1 extends StandardsForEfficientCryptography with secp521r1Platform with Prime with Random:
  def bitLength: Int = 521
  def curveOrder: Int = 1
  override def oid: Option[ObjectIdentifier] = Some(ObjectIdentifier.unsafeFromString("1.3.132.0.35"))
end secp521r1
object secp521r1 extends secp521r1
