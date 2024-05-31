package com.peknight.security.cipher.padding

trait OAEPPadding extends CipherAlgorithmPadding with OAEP:
  val padding: String = "OAEPPadding"
end OAEPPadding
object OAEPPadding extends OAEPPadding
