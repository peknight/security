package com.peknight.security.cipher.padding

import com.peknight.security.digest.Digest
import com.peknight.security.mgf.MGF

trait OAEPWithDigestAndMGFPadding extends CipherAlgorithmPadding with OAEP:
  def digest: Digest
  def mgf: MGF
  def padding: String = s"OAEPWith${digest.abbreviation}And${mgf.mgf}Padding"
end OAEPWithDigestAndMGFPadding
object OAEPWithDigestAndMGFPadding:
  private case class OAEPWithDigestAndMGFPadding(digest: Digest, mgf: MGF)
    extends com.peknight.security.cipher.padding.OAEPWithDigestAndMGFPadding
  def apply(digest: Digest, mgf: MGF): com.peknight.security.cipher.padding.OAEPWithDigestAndMGFPadding =
    OAEPWithDigestAndMGFPadding(digest, mgf)
end OAEPWithDigestAndMGFPadding
