package com.peknight.security.pbe

import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.CipherAlgorithmPadding
import com.peknight.security.cipher.{CipherAlgorithm, DES}
import com.peknight.security.digest.{MD, MessageDigestAlgorithm}

trait PBES1WithDigestAndEncryption extends PBEWithDigestAndEncryption:
  def pbe: PBES1 = PBES1
  def digest: MD
  def encryption: DES
end PBES1WithDigestAndEncryption
object PBES1WithDigestAndEncryption:
  private[this] case class PBES1WithDigestAndEncryption(digest: MD, encryption: DES)
    extends com.peknight.security.pbe.PBES1WithDigestAndEncryption
  def apply(digest: MD, encryption: DES): com.peknight.security.pbe.PBES1WithDigestAndEncryption =
    PBES1WithDigestAndEncryption(digest, encryption)
end PBES1WithDigestAndEncryption
