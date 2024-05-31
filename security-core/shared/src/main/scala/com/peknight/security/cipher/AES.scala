package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}
import com.peknight.security.key.generator.KeyGeneratorAlgorithm
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm
import com.peknight.security.parameter.AlgorithmParametersAlgorithm

/**
 * Advanced Encryption Standard
 */
trait AES extends CipherAlgorithm with Symmetric:
  type This = AES
  def keySize: Int = 128
  def /(mode: CipherAlgorithmMode): This = AES(mode, padding)
  def /(padding: CipherAlgorithmPadding): This = AES(mode, padding)
  def algorithm: String = "AES"
end AES
object AES extends AES with AlgorithmParametersAlgorithm with KeyGeneratorAlgorithm with SecretKeyFactoryAlgorithm:
  private[this] case class AES(override val mode: CipherAlgorithmMode, override val padding: CipherAlgorithmPadding)
    extends com.peknight.security.cipher.AES
  def apply(mode: CipherAlgorithmMode = NONE, padding: CipherAlgorithmPadding = NoPadding)
  : com.peknight.security.cipher.AES =
    (mode, padding) match
      case (NONE, NoPadding) => this
      case _ => AES(mode, padding)
end AES
