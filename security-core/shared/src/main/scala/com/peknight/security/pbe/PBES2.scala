package com.peknight.security.pbe

import com.peknight.security.cipher.CipherAlgorithm
import com.peknight.security.digest.Digest

/**
 * Password-Based Encryption Scheme 2
 */
trait PBES2 extends PBE
object PBES2 extends PBES2:
  def withDigestAndEncryption(digest: Digest, encryption: CipherAlgorithm): PBES2WithDigestAndEncryption =
    PBES2WithDigestAndEncryption(digest, encryption)
end PBES2
