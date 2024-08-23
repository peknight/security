package com.peknight.security.error

import com.peknight.error.Error
import com.peknight.error.std.WrongClassTag

import scala.reflect.ClassTag

trait UncheckedKey[E] extends SecurityError with WrongClassTag[E]:
  override protected def lowPriorityMessage: Option[String] =
    Some(s"Unchecked key${actualType.fold("")(actual => s": $actual")}, expecting: $expectedType")
end UncheckedKey
object UncheckedKey:
  private case class UncheckedKey[E](expectedClassTag: ClassTag[E], override val actualType: Option[String])
    extends com.peknight.security.error.UncheckedKey[E]
  def apply[E](using classTag: ClassTag[E]): com.peknight.security.error.UncheckedKey[E] =
    UncheckedKey(classTag, None)
  def apply[E](a: Any)(using classTag: ClassTag[E]): com.peknight.security.error.UncheckedKey[E] =
    UncheckedKey(classTag, Some(Error.errorType(a)))
end UncheckedKey
