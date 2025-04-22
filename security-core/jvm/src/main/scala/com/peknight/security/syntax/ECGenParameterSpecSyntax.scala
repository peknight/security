package com.peknight.security.syntax

import cats.effect.Sync
import com.peknight.security.ecc.EC
import com.peknight.security.provider.Provider

import java.security.{KeyPair, SecureRandom, Provider as JProvider}
import java.security.spec.ECGenParameterSpec

trait ECGenParameterSpecSyntax:
  extension (params: ECGenParameterSpec)
    def generateKeyPair[F[_]: Sync](random: Option[SecureRandom] = None, provider: Option[Provider | JProvider] = None)
    : F[KeyPair] =
      EC.paramsGenerateKeyPair[F](params, random, provider)
  end extension
end ECGenParameterSpecSyntax
object ECGenParameterSpecSyntax extends ECGenParameterSpecSyntax
