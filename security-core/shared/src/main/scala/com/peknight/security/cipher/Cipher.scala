package com.peknight.security.cipher

import com.peknight.security.algorithm.{Algorithm, ServiceName}

trait Cipher extends Algorithm
object Cipher extends CipherCompanion with ServiceName:
  def serviceName: String = "Cipher"
end Cipher
