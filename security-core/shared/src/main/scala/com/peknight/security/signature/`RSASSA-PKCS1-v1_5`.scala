package com.peknight.security.signature

import com.peknight.security.cipher.RSA
import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-256`}
import com.peknight.security.padding.{Padding, `PKCS1-v1_5`}

trait `RSASSA-PKCS1-v1_5` extends RSASSA with `RSASSA-PKCS1-v1_5Platform`:
  def signature: DigestWithEncryption = digest.withEncryption(RSA)
  def padding: Padding = `PKCS1-v1_5`
end `RSASSA-PKCS1-v1_5`
object `RSASSA-PKCS1-v1_5` extends `RSASSA-PKCS1-v1_5`:
  def digest: MessageDigestAlgorithm = `SHA-256`
  private case class `RSASSA-PKCS1-v1_5`(digest: MessageDigestAlgorithm)
    extends com.peknight.security.signature.`RSASSA-PKCS1-v1_5`
  def apply(digest: MessageDigestAlgorithm): com.peknight.security.signature.`RSASSA-PKCS1-v1_5` =
    `RSASSA-PKCS1-v1_5`(digest)
end `RSASSA-PKCS1-v1_5`
