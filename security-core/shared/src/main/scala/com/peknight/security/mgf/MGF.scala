package com.peknight.security.mgf

/**
 * Mask Generation Function
 */
trait MGF derives CanEqual:
  def mgf: String
end MGF
