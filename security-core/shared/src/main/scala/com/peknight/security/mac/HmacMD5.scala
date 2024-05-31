package com.peknight.security.mac

import com.peknight.security.digest.{MD5, MessageDigestAlgorithm}
import com.peknight.security.key.generator.KeyGeneratorAlgorithm

trait HmacMD5 extends Hmac with KeyGeneratorAlgorithm:
  val digest: MessageDigestAlgorithm = MD5
end HmacMD5
object HmacMD5 extends HmacMD5
