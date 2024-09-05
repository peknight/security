package com.peknight.security.cipher

import com.peknight.security.cipher.padding.`OAEPWithSHA-512AndMGF1Padding`
import com.peknight.security.padding.Padding

trait `RSAES-OAEPWithSHA-512AndMGF1Padding` extends RSAES:
  def padding: Padding = `OAEPWithSHA-512AndMGF1Padding`
end `RSAES-OAEPWithSHA-512AndMGF1Padding`
object `RSAES-OAEPWithSHA-512AndMGF1Padding` extends `RSAES-OAEPWithSHA-512AndMGF1Padding`
