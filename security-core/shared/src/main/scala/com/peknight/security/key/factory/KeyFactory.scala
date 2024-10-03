package com.peknight.security.key.factory

import com.peknight.security.algorithm.ServiceName

object KeyFactory extends ServiceName with KeyFactoryCompanion:
  def serviceName: String = "KeyFactory"
end KeyFactory
