package com.peknight.security.error

trait UnknownAlgorithm extends SecurityError:
  def algorithmName: String
  override protected def lowPriorityMessage: Option[String] = Some(s"Unknown algorithm: $algorithmName")
  override protected def lowPriorityLabelMessage(label: String): Option[String] =
    Some(s"Unknown algorithm: $label-$algorithmName")
end UnknownAlgorithm
object UnknownAlgorithm:
  private case class UnknownAlgorithm(algorithmName: String) extends com.peknight.security.error.UnknownAlgorithm
  def apply(algorithmName: String): com.peknight.security.error.UnknownAlgorithm = UnknownAlgorithm(algorithmName)
end UnknownAlgorithm
