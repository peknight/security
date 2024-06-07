package com.peknight.security.cipher

import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.CipherAlgorithmPadding
import com.peknight.security.mac.MAC

/**
 * Authenticated Encryption with Associated Data
 */
trait AEAD extends CipherAlgorithm:
  def encryption: CipherAlgorithm
  def mac: MAC
  override def mode: CipherAlgorithmMode = encryption.mode
  override def padding: CipherAlgorithmPadding = encryption.padding
  override def -(mac: MAC): AEAD = AEAD(encryption, mac)
  def algorithm: String = s"${encryption.algorithm}-${mac.algorithm}"
end AEAD
object AEAD:
  private case class AEAD(encryption: CipherAlgorithm, mac: MAC)
    extends com.peknight.security.cipher.AEAD:
    type This = com.peknight.security.cipher.AEAD
    def /(mode: CipherAlgorithmMode): This = AEAD(encryption / mode, mac)
    def /(padding: CipherAlgorithmPadding): This = AEAD(encryption / padding, mac)
  end AEAD
  def apply(encryption: CipherAlgorithm, mac: MAC): com.peknight.security.cipher.AEAD = AEAD(encryption, mac)
end AEAD
