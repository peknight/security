package com.peknight.security.mgf

/**
 * Mask Generation Function
 */
trait MGF extends MGFPlatform derives CanEqual:
  def mgf: String
end MGF
