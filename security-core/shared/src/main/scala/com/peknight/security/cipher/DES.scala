package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}
import com.peknight.security.key.generator.KeyGeneratorAlgorithm
import com.peknight.security.key.secret.{SecretKeyAlgorithm, SecretKeyFactoryAlgorithm}
import com.peknight.security.parameter.AlgorithmParametersAlgorithm

/**
 * The Digital Encryption Standard as described in FIPS PUB 46-3.
 */
trait DES extends CipherAlgorithm
  with AlgorithmParametersAlgorithm
  with SecretKeyAlgorithm
  with KeyGeneratorAlgorithm
  with SecretKeyFactoryAlgorithm
  with BlockCipher
  with Symmetric:
  type This <: DES
  def blockSize: Int = 8
  def algorithm: String = "DES"
end DES
object DES extends DES:
  type This = com.peknight.security.cipher.DES
  def /(mode: CipherAlgorithmMode): This = apply(mode, padding)
  def /(padding: CipherAlgorithmPadding): This = apply(mode, padding)
  private case class DES(override val mode: CipherAlgorithmMode, override val padding: CipherAlgorithmPadding)
    extends com.peknight.security.cipher.DES:
    type This = com.peknight.security.cipher.DES
    def /(mode: CipherAlgorithmMode): This = apply(mode, padding)
    def /(padding: CipherAlgorithmPadding): This = apply(mode, padding)
  end DES
  def apply(mode: CipherAlgorithmMode = NONE, padding: CipherAlgorithmPadding = NoPadding)
  : com.peknight.security.cipher.DES =
    (mode, padding) match
      case (NONE, NoPadding) => this
      case _ => DES(mode, padding)
end DES
