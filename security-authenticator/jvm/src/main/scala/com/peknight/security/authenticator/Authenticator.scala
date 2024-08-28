package com.peknight.security.authenticator

import cats.Id
import cats.effect.{Clock, Sync}
import cats.syntax.applicative.*
import cats.syntax.applicativeError.*
import cats.syntax.either.*
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.cats.effect.ext.Clock.realTimeInstant
import com.peknight.codec.base.Base32
import com.peknight.error.Error
import com.peknight.security.mac.HmacSHA1
import com.peknight.security.otp.OneTimePassword
import com.peknight.security.provider.Provider

import java.security.Provider as JProvider
import scala.concurrent.duration.*

object Authenticator:
  def generateOtp[F[_]: Sync: Clock](base32: Base32): F[Either[Error, String]] =
    base32.decode[Id] match
      case Right(key) =>
        for
          instant <- realTimeInstant[F]
          res <- OneTimePassword.generateOtp[F, Throwable](instant.toEpochMilli / 30.seconds.toMillis, 6)(
            HmacSHA1.rawMAC[F](key, _).attempt
          )
        yield res
      case Left(error) => error.asLeft.pure

  def verifyOtp[F[_]: Sync: Clock](base32: Base32): F[Either[Error, Boolean]] =
    ???
end Authenticator
