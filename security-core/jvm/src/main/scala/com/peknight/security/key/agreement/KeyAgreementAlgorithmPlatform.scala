package com.peknight.security.key.agreement

import cats.effect.Sync
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.security.spec.AlgorithmParameterSpec
import java.security.{Key, SecureRandom, Provider as JProvider}
import javax.crypto.KeyAgreement as JKeyAgreement

trait KeyAgreementAlgorithmPlatform { self: KeyAgreementAlgorithm =>
  def getKeyAgreement[F[_]: Sync](provider: Option[Provider | JProvider] = None): F[JKeyAgreement] =
    KeyAgreement.getInstance[F](self, provider)

  def generateSecret[F[_]: Sync](thePartyPrivateKey: Key, otherPartyPublicKey: Key,
                                 params: Option[AlgorithmParameterSpec] = None, random: Option[SecureRandom] = None,
                                 provider: Option[Provider | JProvider] = None): F[ByteVector] =
    KeyAgreement.generateSecret[F](self, thePartyPrivateKey, otherPartyPublicKey, params, random, provider)
}
