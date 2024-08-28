package com.peknight.security.bouncycastle.crypto

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import com.peknight.security.Security
import com.peknight.security.bouncycastle.jce.provider.BouncyCastleProvider
import com.peknight.security.bouncycastle.pbe.PBE
import com.peknight.security.cipher.mode.CBC
import com.peknight.security.cipher.{AES_128, Cipher}
import com.peknight.security.digest.`SHA-1`
import com.peknight.security.random.SecureRandom
import com.peknight.security.spec.{PBEKeySpec, PBEParameterSpec}
import com.peknight.security.syntax.key.{decrypt, encrypt}
import com.peknight.security.syntax.secureRandom.nextBytesF
import org.scalatest.flatspec.AsyncFlatSpec
import scodec.bits.ByteVector

class CipherFlatSpec extends AsyncFlatSpec with AsyncIOSpec:
  "PBEwithSHA1and128bitAES-CBC-BC" should "succeed" in {
    val run =
      for
        bouncyCastleProvider <- BouncyCastleProvider[IO]
        _ <- Security.addProvider[IO](bouncyCastleProvider)
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        password <- secureRandom.nextBytesF[IO](16).map(_.toHex)
        salt <- secureRandom.nextBytesF[IO](16)
        input <- secureRandom.nextBytesF[IO](32)
        algorithm = PBE.withDigestAndAES(`SHA-1`, AES_128 / CBC)
        secretKey <- algorithm.generateSecret[IO](PBEKeySpec(password))
        pbeParameterSpec = PBEParameterSpec(salt, 1000)
        encrypted <- secretKey.encrypt[IO](input, params = Some(pbeParameterSpec))
        decrypted <- secretKey.decrypt[IO](encrypted, params = Some(pbeParameterSpec))
      yield input === decrypted
    run.asserting(assert)
  }
end CipherFlatSpec
