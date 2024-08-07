package com.peknight.security.spec

import java.security.spec.NamedParameterSpec as JNamedParameterSpec

object NamedParameterSpec:
  def apply(namedParameterSpecName: NamedParameterSpecName): JNamedParameterSpec =
    new JNamedParameterSpec(namedParameterSpecName.parameterSpecName)
end NamedParameterSpec
