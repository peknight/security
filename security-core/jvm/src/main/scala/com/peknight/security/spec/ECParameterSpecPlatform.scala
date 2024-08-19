package com.peknight.security.spec

import cats.effect.Sync
import com.peknight.security.ecc.EC
import com.peknight.security.provider.Provider

import java.security.spec.ECParameterSpec
import java.security.{KeyPair, SecureRandom, Provider as JProvider}

trait ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec
  def generateKeyPair[F[_]: Sync](provider: Option[Provider | JProvider] = None, random: Option[SecureRandom] = None)
  : F[KeyPair] =
    EC.paramsGenerateKeyPair[F](ecParameterSpec, provider, random)
end ECParameterSpecPlatform
