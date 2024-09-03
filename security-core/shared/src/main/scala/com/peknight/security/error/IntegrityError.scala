package com.peknight.security.error

trait IntegrityError extends SecurityError:
  override protected def lowPriorityMessage: Option[String] = Some("Integrity Error")
end IntegrityError
object IntegrityError extends IntegrityError
