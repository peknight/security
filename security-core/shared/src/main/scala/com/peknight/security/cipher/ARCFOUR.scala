package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}
import com.peknight.security.key.generator.KeyGeneratorAlgorithm
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm

/**
 * RC4 not recommend
 */
trait ARCFOUR extends CipherAlgorithm
  with KeyGeneratorAlgorithm
  with SecretKeyFactoryAlgorithm
  with StreamCipher
  with RC:
  def algorithm: String = "ARCFOUR"
end ARCFOUR
object ARCFOUR extends ARCFOUR:
  type This = com.peknight.security.cipher.ARCFOUR
  def /(mode: CipherAlgorithmMode): This = apply(mode, padding)
  def /(padding: CipherAlgorithmPadding): This = apply(mode, padding)
  private case class ARCFOUR(override val mode: CipherAlgorithmMode, override val padding: CipherAlgorithmPadding)
    extends com.peknight.security.cipher.ARCFOUR:
    type This = com.peknight.security.cipher.ARCFOUR
    def /(mode: CipherAlgorithmMode): This = apply(mode, padding)
    def /(padding: CipherAlgorithmPadding): This = apply(mode, padding)
  end ARCFOUR
  def apply(mode: CipherAlgorithmMode = NONE, padding: CipherAlgorithmPadding = NoPadding)
  : com.peknight.security.cipher.ARCFOUR =
    (mode, padding) match
      case (NONE, NoPadding) => this
      case _ => ARCFOUR(mode, padding)
end ARCFOUR
