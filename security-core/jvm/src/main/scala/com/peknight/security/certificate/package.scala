package com.peknight.security

import cats.data.NonEmptyList
import cats.effect.kernel.Resource
import cats.effect.{Async, Clock, Ref}
import cats.syntax.applicative.*
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import cats.syntax.option.none
import cats.syntax.order.*
import com.peknight.cats.instances.time.instant.given
import com.peknight.commons.time.syntax.temporal.-
import com.peknight.error.syntax.applicativeError.*
import com.peknight.fs2.ext.pipe.evalMapChunks
import com.peknight.fs2.ext.resource.ScheduledResource
import com.peknight.security.certificate.factory.X509
import com.peknight.security.key.store.pkcs12
import com.peknight.security.provider.Provider
import fs2.io.toInputStream
import fs2.{Chunk, Pipe, Pull, Stream}

import java.io.InputStream
import java.security.cert.{Certificate, X509Certificate}
import java.security.{KeyPair, KeyStore, Provider as JProvider}
import scala.concurrent.duration.FiniteDuration

package object certificate:
  private def trimming[F[_]]: Pipe[F, Byte, Byte] =
    case class State(startOfFile: Boolean, buffer: Option[Byte])
    def go(state: State, stream: Stream[F, Byte]): Pull[F, Byte, Unit] =
      state.buffer match
        case Some(byte) => processByte(byte, state.copy(buffer = None), stream)
        case None => stream.pull.uncons1.flatMap {
          case Some((byte, tail)) => processByte(byte, state, tail)
          case None => Pull.done
        }

    def processByte(current: Byte, state: State, tail: Stream[F, Byte]): Pull[F, Byte, Unit] =
      if isLineSeparator(current) then handleLineSeparator(current, state, tail)
      else Pull.output1(current) >> go(State(false, None), tail)

    def isLineSeparator(b: Byte): Boolean = b == '\n' || b == '\r'

    def handleLineSeparator(firstByte: Byte, state: State, tail: Stream[F, Byte]): Pull[F, Byte, Unit] =
      if state.startOfFile then
        skipLineSeparators(tail).flatMap {
          case (Some(nonLine), rest) => Pull.output1(nonLine) >> go(State(false, None), rest)
          case (_, rest) => Pull.done
        }
      else
        skipLineSeparators(tail).flatMap { (nonLineOpt, rest) =>
          Pull.output1('\n'.toByte) >> go(State(false, nonLineOpt), rest)
        }

    def skipLineSeparators(s: Stream[F, Byte]): Pull[F, Byte, (Option[Byte], Stream[F, Byte])] =
      s.pull.uncons1.flatMap {
        case Some((byte, rest)) if isLineSeparator(byte) => skipLineSeparators(rest)
        case Some((nonLineSep, rest)) => Pull.pure((Some(nonLineSep), rest))
        case None => Pull.pure((None, Stream.empty))
      }

    in => go(State(true, None), in).stream
  end trimming

  def toX509Certificate[F[_]: Async](provider: Option[Provider | JProvider] = None): Pipe[F, Byte, X509Certificate] =
    _.through(trimming)
      .through(toInputStream)
      .through(evalMapChunks[F, F, InputStream, InputStream, X509Certificate](
        _.traverse(in => X509.generateCertificates[F](in, provider))
          .map(_.flatMap(list => Chunk.from(list.collect { case certificate: X509Certificate => certificate } )))
      ))

  def scheduledKeyStoreResource[F[_]: Async, A](scheduler: Stream[F, ?], threshold: FiniteDuration, alias: String,
                                                keyPassword: String, provider: Option[Provider | JProvider])
                                               (initF: F[(NonEmptyList[X509Certificate], KeyPair)])
                                               (nextF: F[(NonEmptyList[X509Certificate], KeyPair)])
                                               (resourceF: (KeyStore, NonEmptyList[X509Certificate], KeyPair) => F[Resource[F, A]])
  : Resource[F, Ref[F, ((NonEmptyList[X509Certificate], KeyPair), A)]] =
    ScheduledResource[F, (NonEmptyList[X509Certificate], KeyPair), A](scheduler)(initF) {
      (certificateChain, keyPair) =>
        Option(certificateChain.head.getNotAfter).map(_.toInstant) match
          case Some(notAfter) =>
            Clock[F].realTimeInstant.flatMap {
              case now if now >= notAfter - threshold => nextF.asError.map(_.toOption)
              case _ => none.pure[F]
            }
          case _ => none.pure[F]
    } { (certificateChain, keyPair) =>
      for
        keyStore <- pkcs12[F](alias, keyPair.getPrivate, keyPassword, certificateChain, provider)
        resource <- resourceF(keyStore, certificateChain, keyPair)
      yield
        resource
    }
end certificate
