package com.peknight.security.signature

import cats.effect.Sync
import com.peknight.security.provider.Provider
import scodec.bits.ByteVector

import java.security.Provider as JProvider
import java.security.interfaces.{EdECPrivateKey, EdECPublicKey}
import java.security.spec.{EdECPublicKeySpec, EdECPrivateKeySpec as JEdECPrivateKeySpec}

trait EdDSAPlatform { self: EdDSA =>
  def publicKeySpec(publicKeyBytes: ByteVector): EdECPublicKeySpec =
    EdDSA.publicKeySpec(self, publicKeyBytes)

  def privateKeySpec(privateKeyBytes: ByteVector): JEdECPrivateKeySpec =
    EdDSA.privateKeySpec(self, privateKeyBytes)

  def publicKey[F[_]: Sync](publicKeyBytes: ByteVector, provider: Option[Provider | JProvider] = None)
  : F[EdECPublicKey] =
    EdDSA.publicKey[F](self, publicKeyBytes, provider)

  def privateKey[F[_]: Sync](privateKeyBytes: ByteVector, provider: Option[Provider | JProvider] = None)
  : F[EdECPrivateKey] =
    EdDSA.privateKey[F](self, privateKeyBytes, provider)
}
