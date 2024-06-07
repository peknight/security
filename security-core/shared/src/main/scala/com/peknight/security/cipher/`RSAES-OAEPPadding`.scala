package com.peknight.security.cipher

import com.peknight.security.cipher.padding.OAEPPadding
import com.peknight.security.padding.Padding

trait `RSAES-OAEPPadding` extends RSAES:
  val padding: Padding = OAEPPadding
end `RSAES-OAEPPadding`
object `RSAES-OAEPPadding` extends `RSAES-OAEPPadding`
