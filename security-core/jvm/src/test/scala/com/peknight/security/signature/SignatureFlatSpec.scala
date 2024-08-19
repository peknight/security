package com.peknight.security.signature

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import com.peknight.security.syntax.secureRandom.nextBytesF
import com.peknight.security.cipher.RSA
import com.peknight.security.key.pair.KeyPairGenerator
import com.peknight.security.random.SecureRandom
import com.peknight.security.digest.`SHA-1`
import org.scalatest.flatspec.AsyncFlatSpec

class SignatureFlatSpec extends AsyncFlatSpec with AsyncIOSpec:
  "SHA1withRSA" should "succeed" in {
    val run =
      for
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        input <- secureRandom.nextBytesF[IO](32)
        keyPair <- KeyPairGenerator.keySizeGenerateKeyPair[IO](RSA, 1024)
        algorithm = `SHA-1`.withEncryption(RSA)
        signed <- Signature.sign[IO](algorithm, keyPair.getPrivate, input)
        res <- Signature.publicKeyVerify[IO](algorithm, keyPair.getPublic, input, signed)
      yield res
    run.asserting(assert)
  }
end SignatureFlatSpec
