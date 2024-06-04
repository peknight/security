package com.peknight.security.pbe

import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.CipherAlgorithmPadding
import com.peknight.security.cipher.{CipherAlgorithm, DES}
import com.peknight.security.digest.{Digest, MD}

trait PBES2WithDigestAndEncryption extends PBEWithDigestAndEncryption:
  def pbe: PBES2 = PBES2
end PBES2WithDigestAndEncryption
object PBES2WithDigestAndEncryption:
  private case class PBES2WithDigestAndEncryption(digest: Digest, encryption: CipherAlgorithm)
    extends com.peknight.security.pbe.PBES2WithDigestAndEncryption
  def apply(digest: Digest, encryption: CipherAlgorithm): com.peknight.security.pbe.PBES2WithDigestAndEncryption =
    PBES2WithDigestAndEncryption(digest, encryption)
end PBES2WithDigestAndEncryption
