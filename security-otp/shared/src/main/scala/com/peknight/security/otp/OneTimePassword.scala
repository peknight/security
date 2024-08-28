package com.peknight.security.otp

import cats.{Applicative, Monad}
import cats.syntax.applicative.*
import cats.syntax.either.*
import cats.syntax.eq.*
import cats.syntax.functor.*
import com.peknight.cats.ext.instances.eitherT.given
import com.peknight.error.Error
import com.peknight.error.syntax.either.{asError, label}
import com.peknight.validation.spire.math.interval.either.{atOrBelow, contains}
import scodec.bits.ByteVector
import spire.math.Interval

import scala.annotation.tailrec

/**
 * An implementation of the HOTP generator specified by RFC 4226.
 *
 * <p>Generates short passcodes that may be used in challenge-response protocols or as timeout
 * passcodes that are only valid for a short period.
 *
 * <p>The default passcode is a 6-digit decimal code. The maximum passcode length is 9 digits.
 *
 * @see https://github.com/google/google-authenticator-android/blob/master/java/com/google/android/apps/authenticator/otp/PasscodeGenerator.java
 */
object OneTimePassword:

  def generateOtp[F[_]: Applicative, E](challenge: ByteVector, codeLength: Int)
                                       (signer: ByteVector => F[Either[E, ByteVector]])
  : F[Either[Error, String]] =
    contains(codeLength, Interval[Int](1, 9)).label("codeLength") match
      case Right(codeLength) => signer(challenge).map(_.map(hash => truncate(hash, codeLength)).asError)
      case Left(error) => error.asLeft.pure

  def generateOtp[F[_]: Applicative, E](state: Long, challenge: ByteVector, codeLength: Int)
                                       (signer: ByteVector => F[Either[E, ByteVector]])
  : F[Either[Error, String]] =
    generateOtp[F, E](challengeToBytes(state, challenge), codeLength)(signer)

  def generateOtp[F[_]: Applicative, E](state: Long, codeLength: Int)
                                       (signer: ByteVector => F[Either[E, ByteVector]])
  : F[Either[Error, String]] =
    generateOtp[F, E](challengeToBytes(state), codeLength)(signer)

  def verifyOtp[F[_]: Applicative, E](oneTimePassword: String, challenge: ByteVector, codeLength: Int)
                                     (signer: ByteVector => F[Either[E, ByteVector]]): F[Either[Error, Boolean]] =
    generateOtp[F, E](challenge, codeLength)(signer).map(_.map(_ === oneTimePassword))

  def verifyOtp[F[_]: Applicative, E](oneTimePassword: String, state: Long, challenge: ByteVector, codeLength: Int)
                                     (signer: ByteVector => F[Either[E, ByteVector]]): F[Either[Error, Boolean]] =
    verifyOtp[F, E](oneTimePassword, challengeToBytes(state, challenge), codeLength)(signer)

  def verifyOtp[F[_]: Applicative, E](oneTimePassword: String, state: Long, codeLength: Int)
                                     (signer: ByteVector => F[Either[E, ByteVector]]): F[Either[Error, Boolean]] =
    verifyOtp[F, E](oneTimePassword, challengeToBytes(state), codeLength)(signer)

  def verifyTimeoutCode[F[_]: Monad, E](timeoutCode: String, startInterval: Long, endInterval: Long, codeLength: Int)
                                       (signer: ByteVector => F[Either[E, ByteVector]]): F[Either[Error, Boolean]] =
    atOrBelow(startInterval, endInterval).label("startInterval") match
      case Right(startInterval) =>
        Monad[[X] =>> F[Either[Error, X]]].tailRecM[Long, Boolean](startInterval) { state =>
          // F[Either[Error, Either[Long, Boolean]]]
          if state <= endInterval then
            verifyOtp[F, E](timeoutCode, state, codeLength)(signer).map {
              case Right(true) => true.asRight.asRight
              case Right(false) => (state + 1).asLeft.asRight
              case Left(error) => error.asLeft
            }
          else false.asRight.asRight.pure
        }
      case Left(error) => error.asLeft.pure
  end verifyTimeoutCode

  private def challengeToBytes(state: Long, challenge: ByteVector): ByteVector =
    val stateBytes = challengeToBytes(state)
    if challenge.isEmpty then stateBytes else stateBytes ++ challenge

  private def challengeToBytes(state: Long): ByteVector =
    ByteVector.fromLong(state)

  /*
   * Dynamically truncate the hash
   */
  private def truncate(hash: ByteVector, codeLength: Int): String =
    // OffsetBits are the low order bits of the last byte of the hash
    val offset = hash.last & 0xF
    // Grab a positive integer value starting at the given offset
    val truncatedHash = hashToInt(hash, offset) & 0x7FFFFFFF
    val pinValue = truncatedHash % Math.pow(10, codeLength).intValue
    padOutput(pinValue, codeLength)

  private def hashToInt(hash: ByteVector, offset: Int): Int =
    ((hash(offset) & 0xFF) << 24) |
      ((hash(offset + 1) & 0xFF) << 16) |
      ((hash(offset + 2) & 0xFF) << 8) |
      (hash(offset + 3) & 0xFF)

  private def padOutput(value: Int, codeLength: Int): String =
    val str = value.toString
    val padLength = codeLength - str.length
    if padLength > 0 then s"${"0".repeat(padLength)}$str" else str

end OneTimePassword
