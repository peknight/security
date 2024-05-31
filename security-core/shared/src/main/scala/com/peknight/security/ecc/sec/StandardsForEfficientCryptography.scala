package com.peknight.security.ecc.sec

import com.peknight.security.ecc.curve.CurveType
import com.peknight.security.ecc.field.FieldType
import com.peknight.security.spec.ECGenParameterSpecName

trait StandardsForEfficientCryptography extends ECGenParameterSpecName with FieldType with CurveType:
  def parameterSpecName: String = s"sec$fieldType$bitLength$curveType$curveOrder"
end StandardsForEfficientCryptography
