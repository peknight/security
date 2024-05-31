package com.peknight.security.cipher.padding

trait NoPadding extends CipherAlgorithmPadding:
  def padding: String = "NoPadding"
end NoPadding
object NoPadding extends NoPadding
