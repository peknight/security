package com.peknight.security.cipher.mode

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import com.peknight.security.cipher.padding.PKCS5Padding
import com.peknight.security.cipher.{AES, Cipher}
import com.peknight.security.random.SecureRandom
import com.peknight.security.syntax.secureRandom.nextBytesF
import fs2.Stream
import org.scalatest.flatspec.AsyncFlatSpec
import scodec.bits.ByteVector

class CipherModeFlatSpec extends AsyncFlatSpec with AsyncIOSpec:
  "AES/ECB/PKCS5Padding" should "pass" in {
    val run =
      for
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        key <- secureRandom.nextBytesF[IO](32)
        input <- secureRandom.nextBytesF[IO](1600)
        algorithm = AES / ECB / PKCS5Padding
        encrypted <- Cipher.rawKeyEncrypt[IO](algorithm, key, input)
        decrypted <- Cipher.rawKeyDecrypt[IO](algorithm, key, encrypted)
        streamEncrypted <- Stream.emits(input.toSeq).covary[IO].through(ECB.encrypt[IO](algorithm, key))
          .compile.toVector.map(ByteVector.apply)
        streamDecrypted <- Stream.emits(streamEncrypted.toSeq).covary[IO].through(ECB.decrypt[IO](algorithm, key))
          .compile.toVector.map(ByteVector.apply)
      yield input === decrypted && input === streamDecrypted && encrypted === streamEncrypted
    run.asserting(assert)
  }

  "AES/CBC/PKCS5Padding" should "pass" in {
    val run =
      for
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        key <- secureRandom.nextBytesF[IO](32)
        input <- secureRandom.nextBytesF[IO](1600)
        iv <- secureRandom.nextBytesF[IO](16)
        algorithm = AES / CBC / PKCS5Padding
        encrypted <- Cipher.rawKeyEncrypt[IO](algorithm, key, input, iv = Some(iv))
        decrypted <- Cipher.rawKeyDecrypt[IO](algorithm, key, encrypted, iv = Some(iv))
        streamEncrypted <- Stream.emits(input.toSeq).covary[IO].through(CBC.encrypt[IO](algorithm, key, iv))
          .compile.toVector.map(ByteVector.apply)
        streamDecrypted <- Stream.emits(streamEncrypted.toSeq).covary[IO].through(CBC.decrypt[IO](algorithm, key, iv))
          .compile.toVector.map(ByteVector.apply)
      yield input === decrypted && input === streamDecrypted && encrypted === streamEncrypted
    run.asserting(assert)
  }
end CipherModeFlatSpec
