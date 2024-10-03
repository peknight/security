package com.peknight.security.syntax

import cats.effect.Sync
import com.peknight.security.ecc.EC
import com.peknight.security.error.SecurityError
import com.peknight.security.provider.Provider
import com.peknight.security.signature.calculateSignatureByteLength

import java.security.interfaces.{ECPrivateKey, ECPublicKey}
import java.security.spec.{ECParameterSpec, ECPoint, ECPrivateKeySpec, ECPublicKeySpec}
import java.security.{KeyPair, SecureRandom, Provider as JProvider}

trait ECParameterSpecSyntax:
  extension (params: ECParameterSpec)
    def bitLength: Int = params.getOrder.bitLength()
    def signatureByteLength: Int = calculateSignatureByteLength(bitLength)
    def generateKeyPair[F[_]: Sync](random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
    : F[KeyPair] =
      EC.paramsGenerateKeyPair[F](params, random, provider)
    def publicKeySpec(x: BigInt, y: BigInt): ECPublicKeySpec = EC.publicKeySpec(x, y, params)
    def privateKeySpec(s: BigInt): ECPrivateKeySpec = EC.privateKeySpec(s, params)
    def publicKey[F[_] : Sync](x: BigInt, y: BigInt, provider: Option[Provider | JProvider] = None): F[ECPublicKey] =
      EC.publicKey[F](x, y, params, provider)
    def privateKey[F[_] : Sync](s: BigInt, provider: Option[Provider | JProvider] = None): F[ECPrivateKey] =
      EC.privateKey[F](s, params, provider)
    def isPointOnCurve(point: ECPoint): Either[SecurityError, Boolean] = EC.isPointOnCurve(point, params)
    def isPointOnCurve(x: BigInt, y: BigInt): Either[SecurityError, Boolean] = EC.isPointOnCurve(x, y, params)
    def checkPointOnCurve(point: ECPoint): Either[SecurityError, Unit] = EC.checkPointOnCurve(point, params)
    def checkPointOnCurve(x: BigInt, y: BigInt): Either[SecurityError, Unit] = EC.checkPointOnCurve(x, y, params)
  end extension
end ECParameterSpecSyntax
object ECParameterSpecSyntax extends ECParameterSpecSyntax