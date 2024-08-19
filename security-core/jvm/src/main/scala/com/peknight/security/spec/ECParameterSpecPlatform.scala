package com.peknight.security.spec

import java.security.spec.ECParameterSpec

trait ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec
end ECParameterSpecPlatform
