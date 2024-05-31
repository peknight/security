package com.peknight.security.algorithm

/**
 * https://docs.oracle.com/en/java/javase/22/docs/specs/security/standard-names.html#cipher-algorithm-names
 */
trait Algorithm derives CanEqual:
  def algorithm: String
  def abbreviation: String = algorithm
end Algorithm
