package com.peknight.security.error

import com.peknight.error.Error

case class PEMMissingEndLine(label: String) extends Error:
  override def lowPriorityMessage: Option[String] = Some(s"PEM missing end line: $label")
end PEMMissingEndLine
