package com.peknight.security.bouncycastle.pkix.syntax

import cats.effect.Sync
import org.bouncycastle.openssl.PEMParser

trait PEMParserSyntax:
  extension (parser: PEMParser)
    def readObjectF[F[_]: Sync, O]: F[O] = Sync[F].blocking(parser.readObject().asInstanceOf[O])
  end extension
end PEMParserSyntax
object PEMParserSyntax extends PEMParserSyntax
