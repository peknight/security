package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}
import com.peknight.security.key.generator.KeyGeneratorAlgorithm
import com.peknight.security.parameter.AlgorithmParametersAlgorithm

trait Blowfish extends CipherAlgorithm with AlgorithmParametersAlgorithm with KeyGeneratorAlgorithm with Symmetric:
  type This = Blowfish
  val algorithm: String = "Blowfish"
  def /(mode: CipherAlgorithmMode): This = Blowfish(mode, padding)
  def /(padding: CipherAlgorithmPadding): This = Blowfish(mode, padding)
end Blowfish
object Blowfish extends Blowfish:
  private[this] case class Blowfish(override val mode: CipherAlgorithmMode, override val padding: CipherAlgorithmPadding)
    extends com.peknight.security.cipher.Blowfish
  def apply(mode: CipherAlgorithmMode = NONE, padding: CipherAlgorithmPadding = NoPadding)
  : com.peknight.security.cipher.Blowfish =
    (mode, padding) match
      case (NONE, NoPadding) => this
      case _ => Blowfish(mode, padding)
end Blowfish
