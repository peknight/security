package com.peknight.security.mac

import com.peknight.security.algorithm.{Algorithm, ServiceName}

trait MAC extends Algorithm
object MAC extends ServiceName:
  def serviceName: String = "Mac"
end MAC
