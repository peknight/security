package com.peknight.security.syntax

import cats.effect.Sync
import cats.syntax.applicative.*
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.provider.Provider
import com.peknight.security.signature.Signature
import com.peknight.security.syntax.signature.*
import scodec.bits.ByteVector

import java.security.cert.X509Certificate
import java.security.spec.AlgorithmParameterSpec
import java.security.{PrivateKey, SecureRandom, Provider as JProvider, Signature as JSignature}

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
  end extension
end X509CertificateSyntax
object X509CertificateSyntax extends X509CertificateSyntax
