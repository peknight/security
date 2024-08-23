package com.peknight.security.error

import com.peknight.error.Error
import com.peknight.error.std.WrongClassTag

import scala.reflect.ClassTag

trait UncheckedPublicKey[E] extends UncheckedKey[E]:
  override protected def lowPriorityMessage: Option[String] =
    Some(s"Unchecked public key${actualType.fold("")(actual => s": $actual")}, expecting: $expectedType")
end UncheckedPublicKey
object UncheckedPublicKey:
  private case class UncheckedPublicKey[E](expectedClassTag: ClassTag[E], override val actualType: Option[String])
    extends com.peknight.security.error.UncheckedPublicKey[E]
  def apply[E](using classTag: ClassTag[E]): com.peknight.security.error.UncheckedPublicKey[E] =
    UncheckedPublicKey(classTag, None)
  def apply[E](a: Any)(using classTag: ClassTag[E]): com.peknight.security.error.UncheckedPublicKey[E] =
    UncheckedPublicKey(classTag, Some(Error.errorType(a)))
end UncheckedPublicKey
