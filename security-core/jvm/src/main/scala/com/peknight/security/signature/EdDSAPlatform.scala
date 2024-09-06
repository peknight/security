package com.peknight.security.signature

import cats.effect.Sync
import com.peknight.security.provider.Provider
import com.peknight.security.spec.{NamedParameterSpec, NamedParameterSpecPlatform}
import scodec.bits.ByteVector

import java.security.interfaces.{EdECPrivateKey, EdECPublicKey}
import java.security.spec.{EdECPrivateKeySpec, EdECPublicKeySpec}
import java.security.{KeyPair, SecureRandom, Provider as JProvider}

trait EdDSAPlatform extends NamedParameterSpecPlatform { self: EdDSA =>
  def generateKeyPair[F[_] : Sync](random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None): F[KeyPair] =
    EdDSA.paramsGenerateKeyPair[F](NamedParameterSpec(self), random, provider)
    
  def publicKeySpec(publicKeyBytes: ByteVector): EdECPublicKeySpec =
    EdDSA.publicKeySpec(self, publicKeyBytes)

  def privateKeySpec(privateKeyBytes: ByteVector): EdECPrivateKeySpec =
    EdDSA.privateKeySpec(self, privateKeyBytes)

  def publicKey[F[_]: Sync](publicKeyBytes: ByteVector, provider: Option[Provider | JProvider] = None)
  : F[EdECPublicKey] =
    EdDSA.publicKey[F](self, publicKeyBytes, provider)

  def privateKey[F[_]: Sync](privateKeyBytes: ByteVector, provider: Option[Provider | JProvider] = None)
  : F[EdECPrivateKey] =
    EdDSA.privateKey[F](self, privateKeyBytes, provider)
}
