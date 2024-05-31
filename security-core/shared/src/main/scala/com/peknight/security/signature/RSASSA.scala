package com.peknight.security.signature

import com.peknight.security.signature.padding.SignaturePadding

trait RSASSA extends Signature with SSA:
  def padding: SignaturePadding
  def algorithm: String = s"RSASSA-${padding.padding}"
end RSASSA
