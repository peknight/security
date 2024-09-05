package com.peknight.security.cipher.padding

trait OAEPPadding extends CipherAlgorithmPadding with OAEP:
  def padding: String = "OAEPPadding"
end OAEPPadding
object OAEPPadding extends OAEPPadding
