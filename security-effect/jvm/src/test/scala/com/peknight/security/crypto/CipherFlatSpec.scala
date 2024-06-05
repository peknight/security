package com.peknight.security.crypto

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import com.peknight.security.SecureRandom
import com.peknight.security.cipher.AES
import com.peknight.security.cipher.mode.CBC
import com.peknight.security.cipher.padding.PKCS5Padding
import com.peknight.security.syntax.secureRandom.{generateSeedF, nextBytesF}
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
        encrypted <- Cipher.encrypt[IO](AES, key)(input)
        _ <- IO.println(s"encrypted=$encrypted")
        decrypted <- Cipher.decrypt[IO](AES, key)(encrypted)
        _ <- IO.println(s"decrypted=$decrypted")
      yield input == decrypted
    run.asserting(result => assert(result))
  }

  "AES/CBC/PKCS5Padding" should "succeed" in {
    val run =
      for
        secureRandom <- SecureRandom.getInstanceStrong[IO]
        key <- secureRandom.nextBytesF[IO](32)
        _ <- IO.println(s"key=$key")
        input <- secureRandom.nextBytesF[IO](32)
        _ <- IO.println(s"input=$input")
        iv <- secureRandom.generateSeedF[IO](16)
        _ <- IO.println(s"iv=$iv")
        encrypted <- Cipher.encrypt[IO](AES / CBC / PKCS5Padding, key, iv)(input)
        _ <- IO.println(s"encrypted=$encrypted")
        decrypted <- Cipher.decrypt[IO](AES / CBC / PKCS5Padding, key, iv)(encrypted)
        _ <- IO.println(s"decrypted=$decrypted")
      yield input == decrypted
    run.asserting(result => assert(result))
  }
end CipherFlatSpec
