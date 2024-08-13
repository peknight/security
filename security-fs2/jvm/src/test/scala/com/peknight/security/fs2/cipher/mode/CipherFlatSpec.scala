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
        _ <- IO.println(s"key=$key")
        input <- secureRandom.nextBytesF[IO](1600)
        _ <- IO.println(s"input=$input")
        algorithm = AES / mode.ECB / PKCS5Padding
        encrypted <- Cipher.rawKeyEncrypt[IO](algorithm, key, input = Some(input))
        _ <- IO.println(s"encrypted=$encrypted")
        decrypted <- Cipher.rawKeyDecrypt[IO](algorithm, key, input = Some(encrypted))
        _ <- IO.println(s"decrypted=$decrypted")
        streamEncrypted <- Stream.emits(input.toSeq).covary[IO].through(ECB.encrypt[IO](algorithm, key))
          .compile.toVector.map(ByteVector.apply)
        _ <- IO.println(s"streamEncrypted=$streamEncrypted")
        streamDecrypted <- Stream.emits(streamEncrypted.toSeq).covary[IO].through(ECB.decrypt[IO](algorithm, key))
          .compile.toVector.map(ByteVector.apply)
        _ <- IO.println(s"streamDecrypted=$streamDecrypted")
      yield input == decrypted && input == streamDecrypted && encrypted == streamEncrypted
    run.asserting(assert)
  }

  "AES/CBC/PKCS5Padding" should "succeed" in {
    val run =
      for
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        key <- secureRandom.nextBytesF[IO](32)
        _ <- IO.println(s"key=$key")
        input <- secureRandom.nextBytesF[IO](1600)
        _ <- IO.println(s"input=$input")
        iv <- secureRandom.nextBytesF[IO](16)
        _ <- IO.println(s"iv=$iv")
        algorithm = AES / mode.CBC / PKCS5Padding
        encrypted <- Cipher.rawKeyEncrypt[IO](algorithm, key, iv = Some(iv), input = Some(input))
        _ <- IO.println(s"encrypted=$encrypted")
        decrypted <- Cipher.rawKeyDecrypt[IO](algorithm, key, iv = Some(iv), input = Some(encrypted))
        _ <- IO.println(s"decrypted=$decrypted")
        streamEncrypted <- Stream.emits(input.toSeq).covary[IO].through(CBC.encrypt[IO](algorithm, key, iv))
          .compile.toVector.map(ByteVector.apply)
        _ <- IO.println(s"streamEncrypted=$streamEncrypted")
        streamDecrypted <- Stream.emits(streamEncrypted.toSeq).covary[IO].through(CBC.decrypt[IO](algorithm, key, iv))
          .compile.toVector.map(ByteVector.apply)
        _ <- IO.println(s"streamDecrypted=$streamDecrypted")
      yield input == decrypted && input == streamDecrypted && encrypted == streamEncrypted
    run.asserting(assert)
  }
end CipherFlatSpec
