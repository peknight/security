package com.peknight.security.padding

trait Padding derives CanEqual:
  def padding: String
  override def toString: String = padding
end Padding
