package com.peknight.security

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import com.peknight.security.syntax.secureRandom.nextBytesF
import com.peknight.security.cipher.RSA
import com.peknight.security.digest.`SHA-1`
import org.scalatest.flatspec.AsyncFlatSpec

class SignatureFlatSpec extends AsyncFlatSpec with AsyncIOSpec:
  "SHA1withRSA" should "succeed" in {
    val run =
      for
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        input <- secureRandom.nextBytesF[IO](32)
        _ <- IO.println(s"input=$input")
        keyPair <- KeyPairGenerator.keySizeGenerateKeyPair[IO](RSA, 1024)
        algorithm = `SHA-1`.withEncryption(RSA)
        signed <- Signature.sign[IO](algorithm, keyPair.getPrivate, input)
        _ <- IO.println(s"signed=$signed")
        res <- Signature.publicKeyVerify[IO](algorithm, keyPair.getPublic, input, signed)
        _ <- IO.println(s"verify=$res")
      yield res
    run.asserting(assert)
  }
end SignatureFlatSpec
