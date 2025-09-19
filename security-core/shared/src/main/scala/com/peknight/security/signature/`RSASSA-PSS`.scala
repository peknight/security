package com.peknight.security.signature

import com.peknight.security.cipher.RSA
import com.peknight.security.digest.{MessageDigestAlgorithm, `SHA-256`}
import com.peknight.security.key.asymmetric.AsymmetricKeyAlgorithm
import com.peknight.security.key.factory.KeyFactoryAlgorithm
import com.peknight.security.key.pair.KeyPairGeneratorAlgorithm
import com.peknight.security.mgf.{MGF, MGF1}
import com.peknight.security.oid.ObjectIdentifier
import com.peknight.security.padding.{PSS, Padding}
import com.peknight.security.parameter.AlgorithmParametersAlgorithm

trait `RSASSA-PSS` extends SignatureAlgorithm
  with AsymmetricKeyAlgorithm
  with AlgorithmParametersAlgorithm
  with KeyFactoryAlgorithm
  with KeyPairGeneratorAlgorithm
  with RSASSA
  with `RSASSA-PSSPlatform`:
  def mgf: MGF = MGF1
  def saltLength: Int = digest.outputLength / 8
  def signature: DigestWithEncryption = digest.withEncryption(RSA, Some(mgf))
  def padding: Padding = PSS
  override def oid: Option[ObjectIdentifier] = Some(ObjectIdentifier.unsafeFromString("1.2.840.113549.1.1.10"))
end `RSASSA-PSS`
object `RSASSA-PSS` extends `RSASSA-PSS`:
  def digest: MessageDigestAlgorithm = `SHA-256`
  private case class `RSASSA-PSS`(digest: MessageDigestAlgorithm, override val mgf: MGF, override val saltLength: Int)
    extends com.peknight.security.signature.`RSASSA-PSS`
  def apply(digest: MessageDigestAlgorithm, mgf: MGF = MGF1, saltLength: Int = digest.outputLength / 8)
  : com.peknight.security.signature.`RSASSA-PSS` =
    `RSASSA-PSS`(digest, mgf, saltLength)
end `RSASSA-PSS`