package com.peknight.security.error

import java.security.spec.ECParameterSpec

trait PointNotOnCurve extends SecurityError:
  def x: BigInt
  def y: BigInt
  def params: ECParameterSpec
  override protected def lowPriorityMessage: Option[String] = Some("Point(x, y) is not on the curve")
end PointNotOnCurve
object PointNotOnCurve:
  private case class PointNotOnCurve(x: BigInt, y: BigInt, params: ECParameterSpec)
    extends com.peknight.security.error.PointNotOnCurve
  def apply(x: BigInt, y: BigInt, params: ECParameterSpec): com.peknight.security.error.PointNotOnCurve =
    PointNotOnCurve(x, y, params)
end PointNotOnCurve
