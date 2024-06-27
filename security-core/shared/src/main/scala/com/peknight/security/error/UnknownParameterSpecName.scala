package com.peknight.security.error

trait UnknownParameterSpecName extends SecurityError:
  def parameterSpecName: String
  override protected def lowPriorityMessage: Option[String] = Some(s"Unknown parameter spec name: $parameterSpecName")
  override protected def lowPriorityLabelMessage(label: String): Option[String] =
    Some(s"Unknown parameter spec name: $label-$parameterSpecName")
end UnknownParameterSpecName
object UnknownParameterSpecName:
  private case class UnknownParameterSpecName(parameterSpecName: String)
    extends com.peknight.security.error.UnknownParameterSpecName
  def apply(parameterSpecName: String): com.peknight.security.error.UnknownParameterSpecName =
    UnknownParameterSpecName(parameterSpecName)
end UnknownParameterSpecName
