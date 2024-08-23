package com.peknight.security.spec

import com.peknight.security.oid.ObjectIdentifier
import com.peknight.security.signature.calculateSignatureByteLength

trait ECGenParameterSpecName extends ParameterSpecName with ECParameterSpecPlatform:
  def bitLength: Int
  def signatureByteLength: Int = calculateSignatureByteLength(bitLength)
  def paramsBitLength: Int = bitLength
  def paramsSignatureByteLength: Int = calculateSignatureByteLength(paramsBitLength)
  def curveOrder: Int
  def oid: Option[ObjectIdentifier] = None
end ECGenParameterSpecName
