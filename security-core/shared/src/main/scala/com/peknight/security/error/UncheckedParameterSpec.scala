package com.peknight.security.error

import com.peknight.error.Error

import scala.reflect.ClassTag

trait UncheckedParameterSpec[A] extends SecurityError:
  def parameterSpecType: ClassTag[A]
  override protected def lowPriorityMessage: Option[String] =
    Some(s"Unchecked parameter spec: ${Error.errorClassTag(using parameterSpecType)}")
object UncheckedParameterSpec:
  private case class UncheckedParameterSpec[A](parameterSpecType: ClassTag[A])
    extends com.peknight.security.error.UncheckedParameterSpec[A]
  def apply[A](using parameterSpecType: ClassTag[A]): com.peknight.security.error.UncheckedParameterSpec[A] =
    UncheckedParameterSpec(parameterSpecType)
end UncheckedParameterSpec
