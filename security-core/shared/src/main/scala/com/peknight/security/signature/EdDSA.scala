package com.peknight.security.signature

import com.peknight.scodec.bits.ext.syntax.byteVector.toUnsignedBigInt
import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm
import com.peknight.security.spec.NamedParameterSpecName
import scodec.bits.ByteVector

/**
 * Edwards-Curve signature algorithm with elliptic curves
 */
trait EdDSA extends DSA
  with SignatureAlgorithm
  with KeyFactoryAlgorithm
  with KeyPairGeneratorAlgorithm
  with NamedParameterSpecName
  with EdDSAPlatform:
  override def algorithm: String = "EdDSA"
  def keyByteLength: Int = 32
  def signatureByteLength: Int = 64
  def parameterSpecName: String = algorithm
end EdDSA
object EdDSA extends EdDSA with EdDSACompanion:
  def xCoordinateOdd(publicKeyBytes: ByteVector): Boolean =
    publicKeyBytes.lastOption.map(_ & -128).exists(_ != 0)
  def yCoordinate(publicKeyBytes: ByteVector): BigInt =
    publicKeyBytes.lastOption
      .fold(publicKeyBytes)(last => publicKeyBytes.init :+ (last & 127).toByte)
      .reverse
      .toUnsignedBigInt
end EdDSA
