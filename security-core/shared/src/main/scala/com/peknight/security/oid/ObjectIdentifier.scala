package com.peknight.security.oid

import cats.data.NonEmptyList
import cats.parse.Numbers.digits
import cats.parse.Parser
import com.peknight.error.parse.ParsingFailure

case class ObjectIdentifier(ids: NonEmptyList[Int]):
  def oid: String = ids.toList.mkString(".")
end ObjectIdentifier
object ObjectIdentifier:
  private def objectIdentifierParser: Parser[ObjectIdentifier] =
    digits.map(_.toInt).repSep(Parser.char('.')).map(ObjectIdentifier.apply)
  def fromString(oid: String): Either[ParsingFailure, ObjectIdentifier] =
    objectIdentifierParser.parseAll(oid).left.map(ParsingFailure.apply)
  def unsafeFromString(oid: String): ObjectIdentifier = fromString(oid).fold(throw _, identity)
end ObjectIdentifier
