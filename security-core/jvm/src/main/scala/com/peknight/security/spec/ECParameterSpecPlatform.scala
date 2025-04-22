package com.peknight.security.spec

import cats.effect.Sync
import com.peknight.security.ecc.EC
import com.peknight.security.error.SecurityError
import com.peknight.security.provider.Provider

import java.security.interfaces.{ECPrivateKey, ECPublicKey}
import java.security.spec.{ECParameterSpec, ECPrivateKeySpec, ECPublicKeySpec, ECPoint as JECPoint}
import java.security.{KeyPair, SecureRandom, Provider as JProvider}

trait ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec
  def curveGenerateKeyPair[F[_]: Sync](random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[KeyPair] =
    EC.paramsGenerateKeyPair[F](ecParameterSpec, random, provider)
  def publicKeySpec(x: BigInt, y: BigInt): ECPublicKeySpec = EC.publicKeySpec(x, y, ecParameterSpec)
  def privateKeySpec(s: BigInt): ECPrivateKeySpec = EC.privateKeySpec(s, ecParameterSpec)
  def publicKey[F[_]: Sync](x: BigInt, y: BigInt, provider: Option[Provider | JProvider] = None): F[ECPublicKey] =
    EC.publicKey[F](x, y, ecParameterSpec, provider)
  def privateKey[F[_]: Sync](s: BigInt, provider: Option[Provider | JProvider] = None): F[ECPrivateKey] =
    EC.privateKey[F](s, ecParameterSpec, provider)
  def isPointOnCurve(point: JECPoint): Either[SecurityError, Boolean] = EC.isPointOnCurve(point, ecParameterSpec)
  def isPointOnCurve(x: BigInt, y: BigInt): Either[SecurityError, Boolean] = EC.isPointOnCurve(x, y, ecParameterSpec)
  def checkPointOnCurve(point: JECPoint): Either[SecurityError, Unit] = EC.checkPointOnCurve(point, ecParameterSpec)
  def checkPointOnCurve(x: BigInt, y: BigInt): Either[SecurityError, Unit] = EC.checkPointOnCurve(x, y, ecParameterSpec)
end ECParameterSpecPlatform
