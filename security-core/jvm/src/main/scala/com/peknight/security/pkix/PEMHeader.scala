package com.peknight.security.pkix

import cats.parse.Parser

case class PEMHeader(name: String, value: String):
  override def toString: String = s"$name:$value"
end PEMHeader
object PEMHeader:
  val pemHeaderParser: Parser[PEMHeader] =
    ((Parser.charsWhile(ch => ch != ':').string.map(_.trim) <* Parser.char(':')) ~ Parser.anyChar.rep0.string.map(_.trim))
      .map(PEMHeader.apply.tupled)
end PEMHeader
