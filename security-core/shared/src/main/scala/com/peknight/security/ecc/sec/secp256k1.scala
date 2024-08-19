package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.Koblitz
import com.peknight.security.ecc.field.Prime
import com.peknight.security.oid.ObjectIdentifier

trait secp256k1 extends StandardsForEfficientCryptography with secp256k1Platform with Prime with Koblitz:
  def bitLength: Int = 256
  def curveOrder: Int = 1
  override def oid: Option[ObjectIdentifier] = Some(ObjectIdentifier.unsafeFromString("1.3.132.0.10"))
end secp256k1
object secp256k1 extends secp256k1
