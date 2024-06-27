package com.peknight.security.bouncycastle.pbe

import com.peknight.security.cipher.AES
import com.peknight.security.digest.Digest
import com.peknight.security.pbe.PBES2

trait PBE extends PBES2
object PBE extends PBE:
  def withDigestAndAES(digest: Digest, encryption: AES): PBEWithDigestAndAES =
    PBEWithDigestAndAES(digest, encryption)
end PBE
