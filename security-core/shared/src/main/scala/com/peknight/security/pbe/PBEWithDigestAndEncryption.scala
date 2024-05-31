package com.peknight.security.pbe

import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.CipherAlgorithmPadding
import com.peknight.security.cipher.{CipherAlgorithm, DES, DESede}
import com.peknight.security.digest.{Digest, MD}
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm
import com.peknight.security.parameter.AlgorithmParametersAlgorithm

trait PBEWithDigestAndEncryption extends CipherAlgorithm
  with AlgorithmParametersAlgorithm
  with SecretKeyFactoryAlgorithm:
  type This = PBEWithDigestAndEncryption
  def pbe: PBE
  def digest: Digest
  def encryption: CipherAlgorithm
  override def mode: CipherAlgorithmMode = encryption.mode
  override def padding: CipherAlgorithmPadding = encryption.padding
  def /(mode: CipherAlgorithmMode): This = PBEWithDigestAndEncryption(digest, encryption / mode)
  def /(padding: CipherAlgorithmPadding): This = PBEWithDigestAndEncryption(digest, encryption / padding)
  def algorithm: String = s"PBEWith${digest.abbreviation}And${encryption.abbreviation}"
end PBEWithDigestAndEncryption
object PBEWithDigestAndEncryption:
  def apply(digest: Digest, encryption: CipherAlgorithm): PBEWithDigestAndEncryption =
    (digest, encryption) match
      case (d, e: DESede) => PBES2WithDigestAndEncryption(d, e)
      case (d: MD, e: DES) => PBES1WithDigestAndEncryption(d, e)
      case (d, e) => PBES2WithDigestAndEncryption(d, e)
end PBEWithDigestAndEncryption

