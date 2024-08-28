package com.peknight.security.syntax

import cats.effect.Sync
import scodec.bits.ByteVector

import java.security.spec.AlgorithmParameterSpec
import java.security.{Key, SecureRandom}
import javax.crypto.KeyAgreement

trait KeyAgreementSyntax:
  extension (keyAgreement: KeyAgreement)
    def initF[F[_]: Sync](key: Key, params: Option[AlgorithmParameterSpec] = None, random: Option[SecureRandom] = None)
    : F[Unit] =
      Sync[F].blocking {
        (params, random) match
          case (Some(params), Some(random)) => keyAgreement.init(key, params, random)
          case (Some(params), None) => keyAgreement.init(key, params)
          case (None, Some(random)) => keyAgreement.init(key, random)
          case _ => keyAgreement.init(key)
      }

    def doPhaseF[F[_]: Sync](key: Key, lastPhase: Boolean): F[Option[Key]] =
      Sync[F].blocking(Option(keyAgreement.doPhase(key, lastPhase)))

    def generateSecretF[F[_]: Sync]: F[ByteVector] =
      Sync[F].blocking(ByteVector(keyAgreement.generateSecret()))
  end extension
end KeyAgreementSyntax
object KeyAgreementSyntax extends KeyAgreementSyntax
