package com.peknight.security.error

import com.peknight.error.Error
import com.peknight.error.std.WrongClassTag

import scala.reflect.ClassTag

trait UnsupportedECField[E] extends SecurityError with WrongClassTag[E]:
  override protected def lowPriorityMessage: Option[String] =
    Some(s"Unsupported EC Curve Field${actualType.fold("")(actual => s": $actual")}, expecting: $expectedType")
end UnsupportedECField
object UnsupportedECField:
  private case class UnsupportedECField[E](expectedClassTag: ClassTag[E], override val actualType: Option[String])
    extends com.peknight.security.error.UnsupportedECField[E]
  def apply[E](using classTag: ClassTag[E]): com.peknight.security.error.UnsupportedECField[E] =
    UnsupportedECField(classTag, None)
  def apply[E](a: Any)(using classTag: ClassTag[E]): com.peknight.security.error.UnsupportedECField[E] =
    UnsupportedECField(classTag, Some(Error.errorType(a)))
end UnsupportedECField

