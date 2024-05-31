package com.peknight.security.pbe

import com.peknight.security.cipher.DES
import com.peknight.security.digest.MD

/**
 * Password-Based Encryption Scheme 1
 */
trait PBES1 extends PBE
object PBES1 extends PBES1:
  def withDigestAndEncryption(digest: MD, encryption: DES): PBES1WithDigestAndEncryption =
    PBES1WithDigestAndEncryption(digest, encryption)
end PBES1
