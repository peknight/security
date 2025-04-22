package com.peknight.security.signature

trait SignatureAlgorithm extends Signature with SignatureAlgorithmPlatform
object SignatureAlgorithm:
  def raw(alg: String): SignatureAlgorithm =
    new SignatureAlgorithm:
      def algorithm: String = alg
  end raw
end SignatureAlgorithm
