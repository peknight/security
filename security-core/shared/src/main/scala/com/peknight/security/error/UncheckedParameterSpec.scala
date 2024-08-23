package com.peknight.security.error

import com.peknight.error.Error
import com.peknight.error.std.WrongClassTag

import scala.reflect.ClassTag

trait UncheckedParameterSpec[E] extends SecurityError with WrongClassTag[E]:
  override protected def lowPriorityMessage: Option[String] =
    Some(s"Unchecked parameter spec${actualType.fold("")(actual => s": $actual")}, expecting: $expectedType")
end UncheckedParameterSpec
object UncheckedParameterSpec:
  private case class UncheckedParameterSpec[E](expectedClassTag: ClassTag[E], override val actualType: Option[String])
    extends com.peknight.security.error.UncheckedParameterSpec[E]
  def apply[E](using classTag: ClassTag[E]): com.peknight.security.error.UncheckedParameterSpec[E] =
    UncheckedParameterSpec(classTag, None)
  def apply[E](a: Any)(using classTag: ClassTag[E]): com.peknight.security.error.UncheckedParameterSpec[E] =
    UncheckedParameterSpec(classTag, Some(Error.errorType(a)))
end UncheckedParameterSpec
