package com.peknight.security.signature

import cats.effect.Sync
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import com.peknight.security.Security
import com.peknight.security.provider.Provider
import com.peknight.security.spec.PSSParameterSpec
import scodec.bits.ByteVector

import java.security.cert.Certificate
import java.security.spec.{AlgorithmParameterSpec, PSSParameterSpec as JPSSParameterSpec}
import java.security.{PrivateKey, PublicKey, SecureRandom, Provider as JProvider, Signature as JSignature}

trait `RSASSA-PSSPlatform` { self: `RSASSA-PSS` =>
  def pssParameterSpec: JPSSParameterSpec =
    PSSParameterSpec(self.digest, self.mgf, self.mgf.toMGFParameterSpec(self.digest), self.saltLength)

  def getSignaturePS[F[_]: Sync](useLegacyName: Boolean = false, provider: Option[Provider | JProvider] = None)
  : F[JSignature] =
    handleSignature[F, JSignature](useLegacyName)((signature, _) => signature.getSignature[F](provider))

  def signPS[F[_]: Sync](privateKey: PrivateKey, data: ByteVector, useLegacyName: Boolean = false,
                         random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None): F[ByteVector] =
    handleSignature[F, ByteVector](useLegacyName)((signature, params) =>
      signature.sign[F](privateKey, data, params, random, provider)
    )

  def publicKeyVerifyPS[F[_]: Sync](publicKey: PublicKey, data: ByteVector, signed: ByteVector,
                                    useLegacyName: Boolean = false, provider: Option[Provider | JProvider] = None)
  : F[Boolean] =
    handleSignature[F, Boolean](useLegacyName)((signature, params) =>
      signature.publicKeyVerify[F](publicKey, data, signed, params, provider)
    )

  def certificateVerifyPS[F[_]: Sync](certificate: Certificate, data: ByteVector, signed: ByteVector,
                                      useLegacyName: Boolean = false, provider: Option[Provider | JProvider] = None)
  : F[Boolean] =
    handleSignature[F, Boolean](useLegacyName)((signature, params) =>
      signature.certificateVerify[F](certificate, data, signed, params, provider)
    )

  private def handleSignature[F[_]: Sync, A](useLegacyName: Boolean)
                                            (f: (SignatureAlgorithm, Option[AlgorithmParameterSpec]) => F[A]): F[A] =
    for
      available <- Security.isAvailable[F](Signature, `RSASSA-PSS`)
      (signature, params) =
        if available && !useLegacyName then (self, Some(pssParameterSpec))
        else (self.signature, None)
      res <- f(signature, params)
    yield
      res
}
