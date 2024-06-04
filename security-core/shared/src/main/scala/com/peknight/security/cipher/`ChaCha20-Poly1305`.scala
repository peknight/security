package com.peknight.security.cipher

import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.CipherAlgorithmPadding
import com.peknight.security.cipher.{AEAD, CipherAlgorithm}
import com.peknight.security.mac.{MAC, Poly1305}
import com.peknight.security.parameter.AlgorithmParametersAlgorithm

trait `ChaCha20-Poly1305` extends AEAD with AlgorithmParametersAlgorithm with Symmetric:
  type This = `ChaCha20-Poly1305`
  def encryption: ChaCha20 = ChaCha20
  val mac: MAC = Poly1305
  def /(mode: CipherAlgorithmMode): This = `ChaCha20-Poly1305`(encryption / mode)
  def /(padding: CipherAlgorithmPadding): This = `ChaCha20-Poly1305`(encryption / padding)
  override def -(mac: MAC): AEAD = encryption - mac
end `ChaCha20-Poly1305`
object `ChaCha20-Poly1305` extends `ChaCha20-Poly1305`:
  private case class `ChaCha20-Poly1305`(override val encryption: ChaCha20)
    extends com.peknight.security.cipher.`ChaCha20-Poly1305`
  def apply(encryption: ChaCha20 = ChaCha20): com.peknight.security.cipher.`ChaCha20-Poly1305` =
    encryption match
      case ChaCha20 => this
      case _ => `ChaCha20-Poly1305`(encryption)
end `ChaCha20-Poly1305`
