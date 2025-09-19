package com.peknight.security.key.kem

import com.peknight.security.spec.NamedParameterSpecName

trait `ML-KEM-N` extends `ML-KEM` with NamedParameterSpecName with `ML-KEMPlatform`:
  def parameter: Int
  override def algorithm: String = s"ML-KEM-$parameter"
  def parameterSpecName: String = algorithm
end `ML-KEM-N`
