package com.peknight.security.key.agreement

/**
 * Elliptic Curve Menezes-Qu-Vanstone.
 */
trait ECMQV extends KeyAgreementAlgorithm:
  def algorithm: String = "ECMQV"
end ECMQV
object ECMQV extends ECMQV
