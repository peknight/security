package com.peknight.security.digest

import com.peknight.security.algorithm.ServiceName

trait MessageDigest extends Digest
object MessageDigest extends ServiceName with MessageDigestCompanion:
  def serviceName: String = "MessageDigest"
end MessageDigest
