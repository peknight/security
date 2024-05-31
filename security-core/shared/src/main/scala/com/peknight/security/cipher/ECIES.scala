package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.CipherAlgorithm
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}

/**
 * Elliptic Curve Integrated Encryption Scheme
 */
trait ECIES extends CipherAlgorithm with Asymmetric:
  type This = ECIES
  val algorithm: String = "ECIES"
  def /(mode: CipherAlgorithmMode): This = ECIES(mode, padding)
  def /(padding: CipherAlgorithmPadding): This = ECIES(mode, padding)
end ECIES
object ECIES extends ECIES:
  private[this] case class ECIES(override val mode: CipherAlgorithmMode, override val padding: CipherAlgorithmPadding)
    extends com.peknight.security.cipher.ECIES
  def apply(mode: CipherAlgorithmMode = NONE, padding: CipherAlgorithmPadding = NoPadding)
  : com.peknight.security.cipher.ECIES =
    (mode, padding) match
      case (NONE, NoPadding) => this
      case _ => ECIES(mode, padding)
end ECIES
