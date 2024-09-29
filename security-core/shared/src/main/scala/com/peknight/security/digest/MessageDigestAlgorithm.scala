package com.peknight.security.digest

import com.peknight.security.algorithm.Algorithm
import com.peknight.security.format.Format
import com.peknight.security.mgf.MGF
import com.peknight.security.signature.DigestWithEncryption

trait MessageDigestAlgorithm extends MessageDigest with MessageDigestAlgorithmPlatform:
  def bitLength: Int
  def outputLength: Int = bitLength
  def withEncryption(encryption: Algorithm, mgf: Option[MGF] = None, format: Option[Format] = None)
  : DigestWithEncryption =
    DigestWithEncryption(this, encryption, mgf, format)
end MessageDigestAlgorithm
