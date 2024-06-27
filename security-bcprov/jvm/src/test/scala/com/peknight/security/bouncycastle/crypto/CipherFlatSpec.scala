package com.peknight.security.bouncycastle.crypto

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import com.peknight.security.bouncycastle.jce.provider.BouncyCastleProvider
import com.peknight.security.bouncycastle.pbe.PBE
import com.peknight.security.cipher.AES_128
import com.peknight.security.cipher.mode.CBC
import com.peknight.security.crypto.spec.{PBEKeySpec, PBEParameterSpec}
import com.peknight.security.crypto.syntax.secretKeyFactory.generateSecretF
import com.peknight.security.crypto.{Cipher, SecretKeyFactory}
import com.peknight.security.digest.`SHA-1`
import com.peknight.security.syntax.secureRandom.nextBytesF
import com.peknight.security.{SecureRandom, Security}
import org.scalatest.flatspec.AsyncFlatSpec
import scodec.bits.ByteVector

class CipherFlatSpec extends AsyncFlatSpec with AsyncIOSpec:
  given CanEqual[ByteVector, ByteVector] = CanEqual.derived
  "PBEwithSHA1and128bitAES-CBC-BC" should "succeed" in {
    val run =
      for
        bouncyCastleProvider <- BouncyCastleProvider[IO]
        _ <- Security.addProvider[IO](bouncyCastleProvider)
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        password <- secureRandom.nextBytesF[IO](16).map(_.toHex)
        _ <- IO.println(s"password=$password")
        salt <- secureRandom.nextBytesF[IO](16)
        _ <- IO.println(s"salt=$salt")
        input <- secureRandom.nextBytesF[IO](32)
        _ <- IO.println(s"input=$input")
        algorithm = PBE.withDigestAndAES(`SHA-1`, AES_128 / CBC)
        secretKeyFactory <- SecretKeyFactory.getInstance[IO](algorithm)
        pbeKeySpec <- PBEKeySpec[IO](password)
        secretKey <- secretKeyFactory.generateSecretF[IO](pbeKeySpec)
        pbeParameterSpec <- PBEParameterSpec[IO](salt, 1000)
        encrypted <- Cipher.encrypt[IO](algorithm, secretKey, pbeParameterSpec)(input)
        _ <- IO.println(s"encrypted=$encrypted")
        decrypted <- Cipher.decrypt[IO](algorithm, secretKey, pbeParameterSpec)(encrypted)
        _ <- IO.println(s"decrypted=$decrypted")
      yield input == decrypted
    run.asserting(assert)
  }
end CipherFlatSpec

