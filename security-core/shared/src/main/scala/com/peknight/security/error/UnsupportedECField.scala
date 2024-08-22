package com.peknight.security.error

import com.peknight.error.Error

import scala.reflect.ClassTag

trait UnsupportedECField[A] extends SecurityError:
  def ecFieldType: ClassTag[A]
  override protected def lowPriorityMessage: Option[String] =
    Some(s"Unsupported EC Curve Field: ${Error.errorClassTag(using ecFieldType)}")
end UnsupportedECField
object UnsupportedECField:
  private case class UnsupportedECField[A](ecFieldType: ClassTag[A])
    extends com.peknight.security.error.UnsupportedECField[A]
  def apply[A](using ecFieldType: ClassTag[A]): com.peknight.security.error.UnsupportedECField[A] =
    UnsupportedECField(ecFieldType)
end UnsupportedECField

