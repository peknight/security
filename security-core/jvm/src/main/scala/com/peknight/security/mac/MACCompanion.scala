package com.peknight.security.mac

import cats.effect.Sync
import cats.syntax.applicativeError.*
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.error.Error
import com.peknight.error.syntax.either.asError
import com.peknight.security.error.{IntegrityError, SecurityError}
import com.peknight.security.provider.Provider
import com.peknight.security.syntax.mac.{doFinalF, initF, rawInitF}
import com.peknight.validation.std.either.isTrue
import scodec.bits.ByteVector

import java.security.spec.AlgorithmParameterSpec
import java.security.{Key, Provider as JProvider}
import javax.crypto.Mac

trait MACCompanion:
  def getInstance[F[_]: Sync](algorithm: MACAlgorithm, provider: Option[Provider | JProvider] = None): F[Mac] =
   Sync[F].blocking {
     provider match
       case Some(provider: Provider) => Mac.getInstance(algorithm.algorithm, provider.name)
       case Some(provider: JProvider) => Mac.getInstance(algorithm.algorithm, provider)
       case _ => Mac.getInstance(algorithm.algorithm)
   }

  def mac[F[_]: Sync](algorithm: MACAlgorithm, key: Key, input: ByteVector,
                      params: Option[AlgorithmParameterSpec] = None,
                      provider: Option[Provider | JProvider] = None): F[ByteVector] =
    for
      m <- getInstance[F](algorithm, provider)
      _ <- m.initF(key, params)
      res <- m.doFinalF[F](input)
    yield res

  def rawMAC[F[_]: Sync](algorithm: MACAlgorithm, key: ByteVector, input: ByteVector,
                         params: Option[AlgorithmParameterSpec] = None,
                         provider: Option[Provider | JProvider] = None): F[ByteVector] =
    for
      m <- getInstance[F](algorithm, provider)
      _ <- m.rawInitF(key, params)
      res <- m.doFinalF[F](input)
    yield res

  def verify[F[_]: Sync](algorithm: MACAlgorithm, key: Key, input: ByteVector, signed: ByteVector,
                         params: Option[AlgorithmParameterSpec] = None,
                         provider: Option[Provider | JProvider] = None): F[Boolean] =
    mac[F](algorithm, key, input, params, provider).map(_ === signed)

  def rawVerify[F[_]: Sync](algorithm: MACAlgorithm, key: ByteVector, input: ByteVector, signed: ByteVector,
                            params: Option[AlgorithmParameterSpec] = None,
                            provider: Option[Provider | JProvider] = None): F[Boolean] =
    rawMAC[F](algorithm, key, input, params, provider).map(_ === signed)

  def check[F[_]: Sync](algorithm: MACAlgorithm, key: Key, input: ByteVector, signed: ByteVector,
                        params: Option[AlgorithmParameterSpec] = None,
                        provider: Option[Provider | JProvider] = None): F[Either[Error, Unit]] =
    verify[F](algorithm, key, input, signed, params, provider).attempt.map(_.asError.flatMap(isTrue(_, IntegrityError)))

  def rawCheck[F[_]: Sync](algorithm: MACAlgorithm, key: ByteVector, input: ByteVector, signed: ByteVector,
                           params: Option[AlgorithmParameterSpec] = None,
                           provider: Option[Provider | JProvider] = None): F[Either[Error, Unit]] =
    rawVerify[F](algorithm, key, input, signed, params, provider).attempt.map(_.asError.flatMap(isTrue(_, IntegrityError)))
end MACCompanion
