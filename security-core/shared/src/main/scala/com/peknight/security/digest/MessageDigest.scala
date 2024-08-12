package com.peknight.security.digest

import com.peknight.security.algorithm.ServiceName

trait MessageDigest extends Digest
object MessageDigest extends ServiceName:
  def serviceName: String = "MessageDigest"
end MessageDigest
