package com.peknight.security.cipher.mode

/**
 * Key Wrap With Padding (KWP) mode
 */
trait KWP extends KW:
  override val mode: String = "KWP"
end KWP
object KWP extends KWP
