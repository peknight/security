package com.peknight.security.signature

import com.peknight.security.algorithm.Algorithm
import com.peknight.security.digest.MessageDigestAlgorithm
import com.peknight.security.format.Format
import com.peknight.security.mgf.MGF

trait DigestWithEncryption extends SignatureAlgorithm:
  def digest: MessageDigestAlgorithm
  def encryption: Algorithm
  def mgf: Option[MGF]
  def format: Option[Format]
  def algorithm: String =
    s"${digest.abbreviation}with${encryption.abbreviation}${mgf.fold("")(f => s"and${f.mgf}")}${format.fold("")(f => s"in${f.format}Format")}"
  def mgf(mgf: MGF): DigestWithEncryption = DigestWithEncryption(digest, encryption, Some(mgf), format)
  def format(format: Format): DigestWithEncryption = DigestWithEncryption(digest, encryption, mgf, Some(format))
  def mgf(mgf: Option[MGF]): DigestWithEncryption = DigestWithEncryption(digest, encryption, mgf, format)
  def format(format: Option[Format]): DigestWithEncryption = DigestWithEncryption(digest, encryption, mgf, format)
end DigestWithEncryption
object DigestWithEncryption:
  private case class DigestWithEncryption(digest: MessageDigestAlgorithm, encryption: Algorithm, mgf: Option[MGF],
                                          format: Option[Format])
    extends com.peknight.security.signature.DigestWithEncryption
  def apply(digest: MessageDigestAlgorithm, encryption: Algorithm, mgf: Option[MGF] = None,
            format: Option[Format] = None)
  : com.peknight.security.signature.DigestWithEncryption =
    DigestWithEncryption(digest, encryption, mgf, format)
end DigestWithEncryption
