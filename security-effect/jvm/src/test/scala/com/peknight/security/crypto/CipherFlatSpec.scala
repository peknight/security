package com.peknight.security.crypto

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import com.peknight.security.cipher.mode.CBC
import com.peknight.security.cipher.padding.PKCS5Padding
import com.peknight.security.cipher.{AES, RSA}
import com.peknight.security.syntax.secureRandom.nextBytesF
import com.peknight.security.{KeyPairGenerator, SecureRandom}
import org.scalatest.flatspec.AsyncFlatSpec
import scodec.bits.ByteVector

class CipherFlatSpec extends AsyncFlatSpec with AsyncIOSpec:
  given CanEqual[ByteVector, ByteVector] = CanEqual.derived
  "AES" should "succeed" in {
    val run =
      for
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        key <- secureRandom.nextBytesF[IO](32)
        _ <- IO.println(s"key=$key")
        input <- secureRandom.nextBytesF[IO](32)
        _ <- IO.println(s"input=$input")
        encrypted <- Cipher.rawKeyEncrypt[IO](AES, key, input = Some(input))
        _ <- IO.println(s"encrypted=$encrypted")
        decrypted <- Cipher.rawKeyDecrypt[IO](AES, key, input = Some(encrypted))
        _ <- IO.println(s"decrypted=$decrypted")
      yield input == decrypted
    run.asserting(assert)
  }

  "AES/CBC/PKCS5Padding" should "succeed" in {
    val run =
      for
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        key <- secureRandom.nextBytesF[IO](32)
        _ <- IO.println(s"key=$key")
        input <- secureRandom.nextBytesF[IO](32)
        _ <- IO.println(s"input=$input")
        iv <- secureRandom.nextBytesF[IO](16)
        _ <- IO.println(s"iv=$iv")
        encrypted <- Cipher.rawKeyEncrypt[IO](AES / CBC / PKCS5Padding, key, iv = Some(iv), input = Some(input))
        _ <- IO.println(s"encrypted=$encrypted")
        decrypted <- Cipher.rawKeyDecrypt[IO](AES / CBC / PKCS5Padding, key, iv = Some(iv), input = Some(encrypted))
        _ <- IO.println(s"decrypted=$decrypted")
      yield input == decrypted
    run.asserting(assert)
  }

  "RSA" should "succeed" in {
    val run =
      for
        keyPair <- KeyPairGenerator.keySizeGenerateKeyPair[IO](RSA, 1024)
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        input <- secureRandom.nextBytesF[IO](32)
        _ <- IO.println(s"input=$input")
        encrypted <- Cipher.keyEncrypt[IO](RSA, keyPair.getPublic, input = Some(input))
        _ <- IO.println(s"encrypted=$encrypted")
        decrypted <- Cipher.keyDecrypt[IO](RSA, keyPair.getPrivate, input = Some(encrypted))
        _ <- IO.println(s"decrypted=$decrypted")
      yield input == decrypted
    run.asserting(assert)
  }
end CipherFlatSpec
