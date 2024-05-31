package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}
import com.peknight.security.key.generator.KeyGeneratorAlgorithm
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm
import com.peknight.security.parameter.AlgorithmParametersAlgorithm

/**
 * Triple DESede / 3DESede
 */
trait DESede extends DES:
  type This = DESede
  def /(mode: CipherAlgorithmMode): This = DESede(mode, padding)
  def /(padding: CipherAlgorithmPadding): This = DESede(mode, padding)
  override def algorithm: String = "DESede"
end DESede
object DESede extends DESede with AlgorithmParametersAlgorithm with KeyGeneratorAlgorithm with SecretKeyFactoryAlgorithm:
  private[this] case class DESede(override val mode: CipherAlgorithmMode, override val padding: CipherAlgorithmPadding)
    extends com.peknight.security.cipher.DESede
  def apply(mode: CipherAlgorithmMode = NONE, padding: CipherAlgorithmPadding = NoPadding)
  : com.peknight.security.cipher.DESede =
    (mode, padding) match
      case (NONE, NoPadding) => this
      case _ => DESede(mode, padding)
end DESede
