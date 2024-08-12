package com.peknight.security.cipher

import com.peknight.security.algorithm.NONE
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.{CipherAlgorithmPadding, NoPadding}
import com.peknight.security.mac.MAC

trait CipherAlgorithm extends Cipher:
  type This <: CipherAlgorithm
  def mode: CipherAlgorithmMode = NONE
  def padding: CipherAlgorithmPadding = NoPadding
  def /(mode: CipherAlgorithmMode): This
  def /(padding: CipherAlgorithmPadding): This
  def -(mac: MAC): AEAD = AEAD(this, mac)
  def transformation: String =
    (mode, padding) match
      case (NONE, NoPadding) => algorithm
      case _ => s"$algorithm/${mode.mode}/${padding.padding}"
end CipherAlgorithm