package com.peknight.security.cipher

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import com.peknight.security.cipher.mode.CBC
import com.peknight.security.cipher.padding.PKCS5Padding
import com.peknight.security.cipher.{AES, RSA}
import com.peknight.security.syntax.secureRandom.nextBytesF
import com.peknight.security.key.pair.KeyPairGenerator
import com.peknight.security.random.SecureRandom
import org.scalatest.flatspec.AsyncFlatSpec
import scodec.bits.ByteVector

class CipherFlatSpec extends AsyncFlatSpec with AsyncIOSpec:
  "AES" should "succeed" in {
    val run =
      for
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        key <- secureRandom.nextBytesF[IO](32)
        input <- secureRandom.nextBytesF[IO](32)
        encrypted <- Cipher.rawKeyEncrypt[IO](AES, key, input = Some(input))
        decrypted <- Cipher.rawKeyDecrypt[IO](AES, key, input = Some(encrypted))
      yield input === decrypted
    run.asserting(assert)
  }

  "AES/CBC/PKCS5Padding" should "succeed" in {
    val run =
      for
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        key <- secureRandom.nextBytesF[IO](32)
        input <- secureRandom.nextBytesF[IO](32)
        iv <- secureRandom.nextBytesF[IO](16)
        encrypted <- Cipher.rawKeyEncrypt[IO](AES / CBC / PKCS5Padding, key, iv = Some(iv), input = Some(input))
        decrypted <- Cipher.rawKeyDecrypt[IO](AES / CBC / PKCS5Padding, key, iv = Some(iv), input = Some(encrypted))
      yield input === decrypted
    run.asserting(assert)
  }

  "RSA" should "succeed" in {
    val run =
      for
        keyPair <- KeyPairGenerator.keySizeGenerateKeyPair[IO](RSA, 1024)
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        input <- secureRandom.nextBytesF[IO](32)
        encrypted <- Cipher.keyEncrypt[IO](RSA, keyPair.getPublic, input = Some(input))
        decrypted <- Cipher.keyDecrypt[IO](RSA, keyPair.getPrivate, input = Some(encrypted))
      yield input === decrypted
    run.asserting(assert)
  }
end CipherFlatSpec
