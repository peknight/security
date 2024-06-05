package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}
import com.peknight.security.key.generator.KeyGeneratorAlgorithm
import com.peknight.security.parameter.AlgorithmParametersAlgorithm

trait RC2 extends CipherAlgorithm with AlgorithmParametersAlgorithm with KeyGeneratorAlgorithm with BlockCipher with RC:
  type This = RC2
  val blockSize: Int = 8
  def /(mode: CipherAlgorithmMode): This = RC2(mode, padding)
  def /(padding: CipherAlgorithmPadding): This = RC2(mode, padding)
  val algorithm: String = "RC2"
end RC2
object RC2 extends RC2:
  private case class RC2(override val mode: CipherAlgorithmMode, override val padding: CipherAlgorithmPadding)
    extends com.peknight.security.cipher.RC2
  def apply(mode: CipherAlgorithmMode = NONE, padding: CipherAlgorithmPadding = NoPadding)
  : com.peknight.security.cipher.RC2 =
    (mode, padding) match
      case (NONE, NoPadding) => this
      case _ => RC2(mode, padding)
end RC2
