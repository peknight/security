package com.peknight.security.pbe

import com.peknight.security.cipher.CipherAlgorithm
import com.peknight.security.cipher.mode.CipherAlgorithmMode
import com.peknight.security.cipher.padding.CipherAlgorithmPadding
import com.peknight.security.key.secret.SecretKeyFactoryAlgorithm
import com.peknight.security.parameter.AlgorithmParametersAlgorithm
import com.peknight.security.random.PRF

trait PBEWithPRFAndEncryption extends CipherAlgorithm with AlgorithmParametersAlgorithm with SecretKeyFactoryAlgorithm:
  type This = PBEWithPRFAndEncryption
  def prf: PRF
  def encryption: CipherAlgorithm
  override def mode: CipherAlgorithmMode = encryption.mode
  override def padding: CipherAlgorithmPadding = encryption.padding
  def /(mode: CipherAlgorithmMode): This = PBEWithPRFAndEncryption(prf, encryption / mode)
  def /(padding: CipherAlgorithmPadding): This = PBEWithPRFAndEncryption(prf, encryption / padding)
  def algorithm: String = s"PBEWith${prf.prf}And${encryption.abbreviation}"
end PBEWithPRFAndEncryption
object PBEWithPRFAndEncryption:
  private case class PBEWithPRFAndEncryption(prf: PRF, encryption: CipherAlgorithm)
    extends com.peknight.security.pbe.PBEWithPRFAndEncryption
  def apply(prf: PRF, encryption: CipherAlgorithm): com.peknight.security.pbe.PBEWithPRFAndEncryption =
    PBEWithPRFAndEncryption(prf, encryption)
end PBEWithPRFAndEncryption
