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
}
