package com.peknight.security.key.agreement

import cats.effect.Sync
import com.peknight.security.provider.Provider
import com.peknight.security.spec.{NamedParameterSpec, NamedParameterSpecPlatform}
import scodec.bits.ByteVector

import java.security.interfaces.{XECPrivateKey, XECPublicKey}
import java.security.spec.{XECPrivateKeySpec, XECPublicKeySpec}
import java.security.{KeyPair, SecureRandom, Provider as JProvider}

trait XDHPlatform extends NamedParameterSpecPlatform { self: XDH =>
  def generateKeyPair[F[_]: Sync](random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None): F[KeyPair] =
    XDH.paramsGenerateKeyPair[F](NamedParameterSpec(self), random, provider)

  def publicKeySpec(publicKeyBytes: ByteVector): XECPublicKeySpec =
    XDH.publicKeySpec(self, publicKeyBytes)

  def privateKeySpec(privateKeyBytes: ByteVector): XECPrivateKeySpec =
    XDH.privateKeySpec(self, privateKeyBytes)

  def publicKey[F[_]: Sync](publicKeyBytes: ByteVector, provider: Option[Provider | JProvider] = None)
  : F[XECPublicKey] =
    XDH.publicKey[F](self, publicKeyBytes, provider)

  def privateKey[F[_]: Sync](privateKeyBytes: ByteVector, provider: Option[Provider | JProvider] = None)
  : F[XECPrivateKey] =
    XDH.privateKey[F](self, privateKeyBytes, provider)
}
