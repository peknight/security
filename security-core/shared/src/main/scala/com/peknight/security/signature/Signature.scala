package com.peknight.security.signature

import com.peknight.security.algorithm.{Algorithm, ServiceName}

trait Signature extends Algorithm
object Signature extends SignatureCompanion with ServiceName:
  def serviceName: String = "Signature"
end Signature
