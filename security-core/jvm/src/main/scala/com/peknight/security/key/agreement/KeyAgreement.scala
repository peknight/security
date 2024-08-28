package com.peknight.security.key.agreement

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.provider.Provider
import com.peknight.security.syntax.keyAgreement.{doPhaseF, generateSecretF, initF}
import scodec.bits.ByteVector

import java.security.spec.AlgorithmParameterSpec
import java.security.{Key, SecureRandom, Provider as JProvider}
import javax.crypto.KeyAgreement as JKeyAgreement

object KeyAgreement:
  def getInstance[F[_]: Sync](algorithm: KeyAgreementAlgorithm, provider: Option[Provider | JProvider] = None)
  : F[JKeyAgreement] =
    Sync[F].blocking {
      provider match
        case Some(provider: Provider) => JKeyAgreement.getInstance(algorithm.algorithm, provider.name)
        case Some(provider: JProvider) => JKeyAgreement.getInstance(algorithm.algorithm, provider)
        case _ => JKeyAgreement.getInstance(algorithm.algorithm)
    }

  def generateSecret[F[_]: Sync](algorithm: KeyAgreementAlgorithm, thePartyPrivateKey: Key, otherPartyPublicKey: Key,
                                 params: Option[AlgorithmParameterSpec] = None, random: Option[SecureRandom] = None,
                                 provider: Option[Provider | JProvider] = None): F[ByteVector] =
    for
      keyAgreement <- getInstance[F](algorithm, provider)
      _ <- keyAgreement.initF[F](thePartyPrivateKey, params, random)
      _ <- keyAgreement.doPhaseF[F](otherPartyPublicKey, true)
      res <- keyAgreement.generateSecretF[F]
    yield
      res
end KeyAgreement
