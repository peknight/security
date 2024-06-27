package com.peknight.security.bouncycastle.pbe

import com.peknight.security.bouncycastle.algorithm.BouncyCastleAlgorithm
import com.peknight.security.cipher.AES
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.CipherAlgorithmPadding
import com.peknight.security.digest.Digest
import com.peknight.security.pbe.{PBES2, PBES2WithDigestAndEncryption}

trait PBEWithDigestAndAES extends PBES2WithDigestAndEncryption with BouncyCastleAlgorithm:
  override def pbe: PBES2 = PBE
  def encryption: AES
  override def /(mode: CipherAlgorithmMode): This = PBEWithDigestAndAES(digest, encryption / mode)
  override def /(padding: CipherAlgorithmPadding): This = PBEWithDigestAndAES(digest, encryption / padding)
  override def algorithm: String = s"PBEwith${digest.abbreviation}and${encryption.blockSize * 8}bitAES-${mode.mode}-BC"
end PBEWithDigestAndAES
object PBEWithDigestAndAES:
  private case class PBEWithDigestAndAES(digest: Digest, encryption: AES)
    extends com.peknight.security.bouncycastle.pbe.PBEWithDigestAndAES
  def apply(digest: Digest, encryption: AES): com.peknight.security.bouncycastle.pbe.PBEWithDigestAndAES =
    PBEWithDigestAndAES(digest, encryption)
end PBEWithDigestAndAES
