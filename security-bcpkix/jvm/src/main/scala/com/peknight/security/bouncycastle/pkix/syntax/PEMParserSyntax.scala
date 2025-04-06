package com.peknight.security.bouncycastle.pkix.syntax

import cats.effect.Sync
import org.bouncycastle.openssl.PEMParser

import scala.reflect.ClassTag

trait PEMParserSyntax:
  extension (parser: PEMParser)
    def readObjectF[F[_]: Sync]: F[Option[AnyRef]] =
      Sync[F].blocking(Option(parser.readObject()))
  end extension
end PEMParserSyntax
object PEMParserSyntax extends PEMParserSyntax
