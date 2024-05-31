package com.peknight.security.spec

trait ECGenParameterSpecName extends ParameterSpecName:
  def bitLength: Int
  def curveOrder: Int
end ECGenParameterSpecName
