package com.peknight.security.fs2.cipher.mode

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import com.peknight.security.cipher.padding.PKCS5Padding
import com.peknight.security.cipher.{AES, Cipher, mode}
import com.peknight.security.random.SecureRandom
import com.peknight.security.syntax.secureRandom.nextBytesF
import fs2.Stream
import org.scalatest.flatspec.AsyncFlatSpec
import scodec.bits.ByteVector

class CipherFlatSpec extends AsyncFlatSpec with AsyncIOSpec:
  given CanEqual[ByteVector, ByteVector] = CanEqual.derived
  "AES/ECB/PKCS5Padding" should "succeed" in {
    val run =
      for
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        key <- secureRandom.nextBytesF[IO](32)
        input <- secureRandom.nextBytesF[IO](1600)
        algorithm = AES / mode.ECB / PKCS5Padding
        encrypted <- Cipher.rawKeyEncrypt[IO](algorithm, key, input = Some(input))
        decrypted <- Cipher.rawKeyDecrypt[IO](algorithm, key, input = Some(encrypted))
        streamEncrypted <- Stream.emits(input.toSeq).covary[IO].through(ECB.encrypt[IO](algorithm, key))
          .compile.toVector.map(ByteVector.apply)
        streamDecrypted <- Stream.emits(streamEncrypted.toSeq).covary[IO].through(ECB.decrypt[IO](algorithm, key))
          .compile.toVector.map(ByteVector.apply)
      yield input == decrypted && input == streamDecrypted && encrypted == streamEncrypted
    run.asserting(assert)
  }

  "AES/CBC/PKCS5Padding" should "succeed" in {
    val run =
      for
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        key <- secureRandom.nextBytesF[IO](32)
        input <- secureRandom.nextBytesF[IO](1600)
        iv <- secureRandom.nextBytesF[IO](16)
        algorithm = AES / mode.CBC / PKCS5Padding
        encrypted <- Cipher.rawKeyEncrypt[IO](algorithm, key, iv = Some(iv), input = Some(input))
        decrypted <- Cipher.rawKeyDecrypt[IO](algorithm, key, iv = Some(iv), input = Some(encrypted))
        streamEncrypted <- Stream.emits(input.toSeq).covary[IO].through(CBC.encrypt[IO](algorithm, key, iv))
          .compile.toVector.map(ByteVector.apply)
        streamDecrypted <- Stream.emits(streamEncrypted.toSeq).covary[IO].through(CBC.decrypt[IO](algorithm, key, iv))
          .compile.toVector.map(ByteVector.apply)
      yield input == decrypted && input == streamDecrypted && encrypted == streamEncrypted
    run.asserting(assert)
  }
end CipherFlatSpec
