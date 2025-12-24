package com.peknight.security.pkix

import cats.effect.Sync
import cats.parse.Parser
import cats.syntax.applicative.*
import cats.syntax.applicativeError.*
import cats.syntax.either.*
import cats.syntax.eq.*
import cats.syntax.flatMap.*
import cats.syntax.monadError.*
import cats.{Foldable, Id, Monad, MonadError}
import com.peknight.codec.base.Base64
import com.peknight.error.Error
import com.peknight.error.syntax.either.asError
import com.peknight.fs2.pipe.*
import com.peknight.security.error.{PEMLabelNotMatch, PEMMissingEndLine}
import com.peknight.security.pkix.PEMObject.{BeginLine, EndLine}
import com.peknight.security.provider.Provider
import fs2.text.lines
import fs2.{Chunk, Pipe}

import java.security.Provider as JProvider
import java.security.cert.{Certificate, X509Certificate}

package object pipe:
  object pemObject:
    def encode[F[_]]: Pipe[F, PEMObject, String] =
      _.mapChunks(chunk => chunk.flatMap(pemObject => Chunk.from(pemObject.lines)))
    def decode[F[_]](using MonadError[F, Throwable]): Pipe[F, String, PEMObject] =
      sealed trait State derives CanEqual
      case object Empty extends State
      case class Stub(label: String, reversedHeaders: List[PEMHeader], reversedData: List[Base64]) extends State
      def go(state: State, chunk: Chunk[String]): F[(State, Chunk[PEMObject])] =
        Monad[F].tailRecM[(List[String], State, List[PEMObject]), (State, Chunk[PEMObject])]((chunk.toList, state, Nil)) {
          case (head :: tail, Empty, reversedObject) =>
            PEMObject.beginLineParser.parseAll(head)
              .map { case BeginLine(label) => (tail, Stub(label, Nil, Nil), reversedObject).asLeft }
              .asError.pure[F].rethrow
          case (head :: tail, Stub(label, reversedHeaders, reversedData), reversedObject) =>
            (PEMObject.endLineParser | PEMHeader.pemHeaderParser.backtrack | Base64.baseParser).parseAll(head) match
              case Right(header: PEMHeader) =>
                (tail, Stub(label, header :: reversedHeaders, reversedData), reversedObject).asLeft.pure[F]
              case Right(base64: Base64) =>
                (tail, Stub(label, reversedHeaders, base64 :: reversedData), reversedObject).asLeft.pure[F]
              case Right(EndLine(endLineLabel)) if endLineLabel === label =>
                val pemObject = PEMObject(label, reversedHeaders.reverse, Foldable[List].fold(reversedData.reverse))
                (tail, Empty, pemObject :: reversedObject).asLeft.pure[F]
              case Right(EndLine(endLineLabel)) => PEMLabelNotMatch(label, endLineLabel).raiseError
              case Left(error) => Error(error).raiseError
          case (Nil, state, reversedObject) => (state, Chunk.from(reversedObject.reverse)).asRight.pure[F]
        }
      _.through(lines).map(_.trim).filter(_.nonEmpty)
        .through(evalScanChunksInitLast[F, F, String, String, PEMObject, State](Empty)(go){ (state, chunk) =>
          go(state, chunk).flatMap {
            case (Empty, chunk) => chunk.pure[F]
            case (Stub(label, _, _), _) => PEMMissingEndLine(label).raiseError
          }
        })
  end pemObject
  object certificate:
    def encode[F[_]]: Pipe[F, Certificate, PEMObject] =
      _.map(cert => PEMObject.encodeCertificate[Id, Certificate].encode(cert))
  end certificate
  object x509Certificate:
    def decode[F[_]: Sync](provider: Option[Provider | JProvider] = None): Pipe[F, PEMObject, X509Certificate] =
      _.evalMap(pemObject => PEMObject.decodeX509Certificate[F](provider).decode(pemObject).rethrow)
  end x509Certificate
end pipe
