package com.peknight.security.pbe

import com.peknight.security.cipher.CipherAlgorithm
import com.peknight.security.digest.Digest
import com.peknight.security.mac.MACAlgorithm
import com.peknight.security.parameter.AlgorithmParametersAlgorithm
import com.peknight.security.random.PRF

/**
 * Password-Based Encryption
 */
trait PBE
object PBE extends PBE with AlgorithmParametersAlgorithm:
  val algorithm: String = "PBE"
  def withDigestAndEncryption(digest: Digest, encryption: CipherAlgorithm): PBEWithDigestAndEncryption =
    PBEWithDigestAndEncryption(digest, encryption)
  def withPRFAndEncryption(prf: PRF, encryption: CipherAlgorithm): PBEWithPRFAndEncryption =
    PBEWithPRFAndEncryption(prf, encryption)
  def withMAC(mac: MACAlgorithm): PBEWithMAC = PBEWithMAC(mac)
end PBE
