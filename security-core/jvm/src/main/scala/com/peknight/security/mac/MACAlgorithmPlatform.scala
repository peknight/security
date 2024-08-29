package com.peknight.security.mac

import cats.effect.Sync
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.security.spec.AlgorithmParameterSpec
import java.security.{Key, Provider as JProvider}
import javax.crypto.Mac

trait MACAlgorithmPlatform { self: MACAlgorithm =>
  def getMAC[F[_]: Sync](provider: Option[Provider | JProvider] = None): F[Mac] =
    MAC.getInstance[F](self, provider)

  def mac[F[_]: Sync](key: Key, input: ByteVector, params: Option[AlgorithmParameterSpec] = None,
                      provider: Option[Provider | JProvider] = None): F[ByteVector] =
    MAC.mac[F](self, key, input, params, provider)

  def rawMAC[F[_]: Sync](key: ByteVector, input: ByteVector, params: Option[AlgorithmParameterSpec] = None,
                         provider: Option[Provider | JProvider] = None): F[ByteVector] =
    MAC.rawMAC[F](self, key, input, params, provider)

  def verify[F[_]: Sync](key: Key, input: ByteVector, signed: ByteVector,
                         params: Option[AlgorithmParameterSpec] = None,
                         provider: Option[Provider | JProvider] = None): F[Boolean] =
    MAC.verify[F](self, key, input, signed, params, provider)

  def rawVerify[F[_]: Sync](key: ByteVector, input: ByteVector, signed: ByteVector,
                            params: Option[AlgorithmParameterSpec] = None,
                            provider: Option[Provider | JProvider] = None): F[Boolean] =
    MAC.rawVerify[F](self, key, input, signed, params, provider)
}
