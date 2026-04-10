package com.peknight.security.syntax

import cats.Applicative
import cats.effect.{Clock, Sync}
import cats.syntax.applicative.*
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import cats.syntax.order.*
import com.peknight.cats.instances.instant.given
import com.peknight.commons.time.syntax.temporal.-
import com.peknight.security.provider.Provider
import com.peknight.security.signature.Signature
import com.peknight.security.syntax.signature.*
import scodec.bits.ByteVector

import java.security.cert.X509Certificate
import java.security.spec.AlgorithmParameterSpec
import java.security.{PrivateKey, SecureRandom, Provider as JProvider, Signature as JSignature}
import scala.concurrent.duration.FiniteDuration

trait X509CertificateSyntax:
  extension (certificate: X509Certificate)
    def getSignature[F[_]: Sync](provider: Option[Provider | JProvider] = None): F[JSignature] =
      Signature.getInstanceRaw[F](certificate.getSigAlgName, provider)

    def sign[F[_]: Sync](privateKey: PrivateKey, data: ByteVector, params: Option[AlgorithmParameterSpec] = None,
                         random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
    : F[ByteVector] =
      for
        signature <- getSignature[F](provider)
        _ <- params.fold(().pure[F])(signature.setParameterF[F])
        _ <- signature.initSignF[F](privateKey, random)
        _ <- signature.updateF[F](data)
        res <- signature.signF[F]
      yield res

    def verify[F[_]: Sync](data: ByteVector, signed: ByteVector, params: Option[AlgorithmParameterSpec] = None,
                           provider: Option[Provider | JProvider] = None): F[Boolean] =
      for
        signature <- getSignature[F](provider)
        _ <- params.fold(().pure[F])(signature.setParameterF[F])
        _ <- signature.initVerifyF[F](certificate)
        _ <- signature.updateF[F](data)
        res <- signature.verifyF[F](signed)
      yield res

    def nearExpiry[F[_]: {Applicative, Clock}](threshold: FiniteDuration): F[Boolean] =
      Option(certificate.getNotAfter).map(_.toInstant) match
        case Some(notAfter) =>
          Clock[F].realTimeInstant.map {
            case now if now >= notAfter - threshold => true
            case _ => false
          }
        case _ => false.pure[F]
  end extension
end X509CertificateSyntax
object X509CertificateSyntax extends X509CertificateSyntax
