package com.peknight.security.signature

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import com.peknight.security.cipher.RSA
import com.peknight.security.digest.`SHA-1`
import com.peknight.security.random.SecureRandom
import com.peknight.security.syntax.secureRandom.nextBytesF
import org.scalatest.flatspec.AsyncFlatSpec

class SignatureFlatSpec extends AsyncFlatSpec with AsyncIOSpec:
  "SHA1withRSA" should "succeed" in {
    val run =
      for
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        input <- secureRandom.nextBytesF[IO](32)
        keyPair <- RSA.keySizeGenerateKeyPair[IO](1024)
        algorithm = `SHA-1`.withEncryption(RSA)
        signed <- algorithm.sign[IO](keyPair.getPrivate, input)
        res <- algorithm.publicKeyVerify[IO](keyPair.getPublic, input, signed)
      yield res
    run.asserting(assert)
  }
end SignatureFlatSpec
