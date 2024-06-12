package com.peknight.security.spec

import com.peknight.security.oid.ObjectIdentifier

trait ECGenParameterSpecName extends ParameterSpecName:
  def bitLength: Int
  def curveOrder: Int
  def oid: Option[ObjectIdentifier] = None
end ECGenParameterSpecName
