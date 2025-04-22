package com.peknight.security.spec

import cats.effect.Sync
import com.peknight.security.ecc.EC
import com.peknight.security.provider.Provider

import java.security.spec.ECGenParameterSpec
import java.security.{KeyPair, SecureRandom, Provider as JProvider}

trait ECGenParameterSpecPlatform { self: ECGenParameterSpecName =>
  def ecGenParameterSpec: ECGenParameterSpec = ECGenParameterSpec(self.parameterSpecName)
  def generateKeyPair[F[_]: Sync](random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
  : F[KeyPair] =
    EC.paramsGenerateKeyPair[F](ecGenParameterSpec, random, provider)
}
end ECGenParameterSpecPlatform
