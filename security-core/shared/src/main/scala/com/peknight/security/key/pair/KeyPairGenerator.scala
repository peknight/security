package com.peknight.security.key.pair

import com.peknight.security.algorithm.ServiceName

object KeyPairGenerator extends ServiceName with KeyPairGeneratorCompanion:
  def serviceName: String = "KeyPairGenerator"
end KeyPairGenerator
