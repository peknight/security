package com.peknight.security.padding

/**
 * Probabilistic Signature Scheme
 */
trait PSS extends Padding:
  def padding: String = "PSS"
end PSS
object PSS extends PSS
