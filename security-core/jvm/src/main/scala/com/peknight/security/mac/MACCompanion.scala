package com.peknight.security.mac

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.provider.Provider
import com.peknight.security.syntax.mac.{doFinalF, initF}
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
  def verify[F[_]: Sync](algorithm: MACAlgorithm, key: Key, input: ByteVector, signed: ByteVector,
                         params: Option[AlgorithmParameterSpec] = None,
                         provider: Option[Provider | JProvider] = None): F[Boolean] =
    mac[F](algorithm, key, input, params, provider).map(_ === signed)
end MACCompanion
