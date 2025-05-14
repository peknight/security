package com.peknight.security.error

import com.peknight.error.Error

case class PEMLabelNotMatch(expected: String, actual: String) extends Error:
  override def lowPriorityMessage: Option[String] = Some(s"PEM label not match: expecting $expected but was $actual.")
end PEMLabelNotMatch
