package com.peknight.security.algorithm

import com.peknight.security.oid.ObjectIdentifier

/**
 * https://docs.oracle.com/en/java/javase/22/docs/specs/security/standard-names.html#cipher-algorithm-names
 */
trait Algorithm derives CanEqual:
  def algorithm: String
  def abbreviation: String = algorithm
  def oid: Option[ObjectIdentifier] = None
  override def toString: String = algorithm
end Algorithm
