package com.peknight.security.error

import com.peknight.error.Error
import com.peknight.error.std.WrongClassTag

import scala.reflect.ClassTag

trait UncheckedPrivateKey[E] extends UncheckedKey[E]:
  override protected def lowPriorityMessage: Option[String] =
    Some(s"Unchecked private key${actualType.fold("")(actual => s": $actual")}, expecting: $expectedType")
end UncheckedPrivateKey
object UncheckedPrivateKey:
  private case class UncheckedPrivateKey[E](expectedClassTag: ClassTag[E], override val actualType: Option[String])
    extends com.peknight.security.error.UncheckedPrivateKey[E]
  def apply[E](using classTag: ClassTag[E]): com.peknight.security.error.UncheckedPrivateKey[E] =
    UncheckedPrivateKey(classTag, None)
  def apply[E](a: Any)(using classTag: ClassTag[E]): com.peknight.security.error.UncheckedPrivateKey[E] =
    UncheckedPrivateKey(classTag, Some(Error.errorType(a)))
end UncheckedPrivateKey
