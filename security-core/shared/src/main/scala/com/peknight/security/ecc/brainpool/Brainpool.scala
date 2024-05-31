package com.peknight.security.ecc.brainpool

import com.peknight.security.ecc.curve.Random
import com.peknight.security.ecc.field.Prime
import com.peknight.security.spec.ECGenParameterSpecName

trait Brainpool extends ECGenParameterSpecName with Prime with Random:
  def parameterSpecName: String = s"brainpool${fieldType.toUpperCase}$bitLength$curveType$curveOrder"
end Brainpool
