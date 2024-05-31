package com.peknight.security.signature.padding

/**
 * Probabilistic Signature Scheme
 */
trait PSS extends SignaturePadding:
  val padding: String = "PSS"
end PSS
object PSS extends PSS
