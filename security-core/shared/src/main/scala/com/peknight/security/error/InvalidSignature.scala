package com.peknight.security.error

trait InvalidSignature extends SecurityError:
  override protected def lowPriorityMessage: Option[String] = Some("Invalid signature")
end InvalidSignature
object InvalidSignature extends InvalidSignature
