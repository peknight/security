package com.peknight.security.signature

import com.peknight.security.spec.NamedParameterSpecName

trait `ML-DSA-N` extends `ML-DSA` with NamedParameterSpecName with `ML-DSAPlatform`:
  def parameter: Int
  override def algorithm: String = s"ML-DSA-$parameter"
  def parameterSpecName: String = algorithm
end `ML-DSA-N`
