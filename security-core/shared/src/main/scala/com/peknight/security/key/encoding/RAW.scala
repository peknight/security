package com.peknight.security.key.encoding

trait RAW extends KeyEncoding:
  val keyEncoding: String = "RAW"
end RAW
object RAW extends RAW
