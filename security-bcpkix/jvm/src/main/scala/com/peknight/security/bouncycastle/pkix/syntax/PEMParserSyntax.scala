package com.peknight.security.bouncycastle.pkix.syntax

import cats.effect.Sync
import cats.syntax.functor.*
import com.peknight.error.Error
import com.peknight.error.syntax.applicativeError.asError
import com.peknight.validation.std.either.typed
import org.bouncycastle.openssl.PEMParser

import scala.reflect.ClassTag

trait PEMParserSyntax:
  extension (parser: PEMParser)
    def readObjectF[F[_]: Sync, O: ClassTag]: F[Either[Error, O]] =
      Sync[F].blocking(typed[O](parser.readObject())).asError.map(_.flatten)
  end extension
end PEMParserSyntax
object PEMParserSyntax extends PEMParserSyntax
