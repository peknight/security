package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}
import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm

/**
 * Rivest–Shamir–Adleman
 */
trait RSA extends CipherAlgorithm with KeyFactoryAlgorithm with KeyPairGeneratorAlgorithm with Asymmetric:
  type This = RSA
  def algorithm: String = "RSA"
  def /(mode: CipherAlgorithmMode): This = RSA(mode, padding)
  def /(padding: CipherAlgorithmPadding): This = RSA(mode, padding)
end RSA
object RSA extends RSA with RSACompanion:
  private case class RSA(override val mode: CipherAlgorithmMode, override val padding: CipherAlgorithmPadding)
    extends com.peknight.security.cipher.RSA
  def apply(mode: CipherAlgorithmMode = NONE, padding: CipherAlgorithmPadding = NoPadding)
  : com.peknight.security.cipher.RSA =
    (mode, padding) match
      case (NONE, NoPadding) => this
      case _ => RSA(mode, padding)
end RSA
