package com.peknight.security.otp

import cats.Id
import cats.effect.Sync
import cats.syntax.applicative.*
import cats.syntax.applicativeError.*
import cats.syntax.either.*
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.cats.effect.ext.Clock.realTimeInstant
import com.peknight.codec.base.Base32
import com.peknight.error.Error
import com.peknight.security.mac.{HmacSHA1, MACAlgorithm}
import com.peknight.security.otp.OneTimePassword
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.security.Provider as JProvider
import scala.concurrent.duration.*

trait OneTimePasswordCompanion { self: OneTimePassword =>
  def generate[F[_]: Sync](base32: Base32, interval: FiniteDuration = 30.seconds, challenge: Option[ByteVector] = None,
                           codeLength: Int = 6, mac: MACAlgorithm = HmacSHA1): F[Either[Error, String]] =
    base32.decode[Id] match
      case Right(key) =>
        for
          instant <- realTimeInstant[F]
          res <- stateGenerate[F, Throwable](instant.toEpochMilli / interval.toMillis, challenge, codeLength)(
            mac.rawMAC[F](key, _).attempt
          )
        yield res
      case Left(error) => error.asLeft.pure

  def verify[F[_]: Sync](base32: Base32, oneTimePassword: String, interval: FiniteDuration = 30.seconds,
                         pastIntervals: Int = 1, futureIntervals: Int = 1, challenge: Option[ByteVector] = None,
                         codeLength: Int = 6, mac: MACAlgorithm = HmacSHA1): F[Either[Error, Boolean]] =
    base32.decode[Id] match
      case Right(key) =>
        for
          instant <- realTimeInstant[F]
          res <- verifyTimeoutCode[F, Throwable](oneTimePassword, instant.toEpochMilli / interval.toMillis,
            pastIntervals, futureIntervals, challenge, codeLength)(mac.rawMAC[F](key, _).attempt)
        yield res
      case Left(error) => error.asLeft.pure
}
