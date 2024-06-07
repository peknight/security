package com.peknight.security.signature

import com.peknight.security.padding.Padding

trait RSASSA extends Signature with SSA:
  def padding: Padding
  def algorithm: String = s"RSASSA-${padding.padding}"
end RSASSA
