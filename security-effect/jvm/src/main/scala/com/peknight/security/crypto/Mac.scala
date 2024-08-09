package com.peknight.security.crypto

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.crypto.syntax.mac.{doFinalF, initF}
import com.peknight.security.mac.MACAlgorithm
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.security.spec.AlgorithmParameterSpec
import java.security.{Key, Provider as JProvider}
import javax.crypto.Mac as JMac

object Mac:
  def getInstance[F[_]: Sync](algorithm: MACAlgorithm, provider: Option[Provider | JProvider] = None): F[JMac] =
   Sync[F].blocking {
     provider match
       case Some(provider: Provider) => JMac.getInstance(algorithm.algorithm, provider.name)
       case Some(provider: JProvider) => JMac.getInstance(algorithm.algorithm, provider)
       case _ => JMac.getInstance(algorithm.algorithm)
   }

  def mac[F[_]: Sync](algorithm: MACAlgorithm, key: Key, provider: Option[Provider | JProvider] = None,
                      params: Option[AlgorithmParameterSpec] = None, input: Option[ByteVector] = None): F[ByteVector] =
    for
      m <- getInstance[F](algorithm, provider)
      _ <- m.initF(key, params)
      res <- m.doFinalF[F](input)
    yield res
end Mac
