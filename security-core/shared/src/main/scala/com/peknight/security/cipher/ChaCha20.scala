package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}
import com.peknight.security.key.generator.KeyGeneratorAlgorithm
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm
import com.peknight.security.mac.{MAC, Poly1305}

trait ChaCha20 extends CipherAlgorithm with KeyGeneratorAlgorithm with SecretKeyFactoryAlgorithm with Symmetric:
  type This = ChaCha20
  val algorithm: String = "ChaCha20"
  def /(mode: CipherAlgorithmMode): This = ChaCha20(mode, padding)
  def /(padding: CipherAlgorithmPadding): This = ChaCha20(mode, padding)
  override def -(mac: MAC): AEAD =
    mac match
      case Poly1305 => `ChaCha20-Poly1305`(this)
      case _ => super.-(mac)
end ChaCha20
object ChaCha20 extends ChaCha20:
  private case class ChaCha20(override val mode: CipherAlgorithmMode, override val padding: CipherAlgorithmPadding)
    extends com.peknight.security.cipher.ChaCha20
  def apply(mode: CipherAlgorithmMode = NONE, padding: CipherAlgorithmPadding = NoPadding)
  : com.peknight.security.cipher.ChaCha20 =
    (mode, padding) match
      case (NONE, NoPadding) => this
      case _ => ChaCha20(mode, padding)
end ChaCha20
