package com.peknight.security.key.agreement

import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm
import com.peknight.security.spec.NamedParameterSpecName

/**
 * Diffie-Hellman key agreement with elliptic curves as defined in RFC 7748.
 */
trait XDH extends DiffieHellman with KeyFactoryAlgorithm with KeyPairGeneratorAlgorithm with NamedParameterSpecName with XDHPlatform:
  def bits: Int = 255
  def keyByteLength: Int = 32
  def prime: BigInt = BigInt("57896044618658097711785492504343953926634992332820282019728792003956564819949")
  override def algorithm: String = "XDH"
  def parameterSpecName: String = algorithm
end XDH
object XDH extends XDH with XDHCompanion
