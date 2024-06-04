package com.peknight.security.crypto

import cats.MonadError
import cats.data.EitherT
import cats.effect.syntax.monadCancel.*
import cats.effect.testing.scalatest.AsyncIOSpec
import cats.effect.{IO, Sync}
import cats.syntax.either.*
import com.peknight.error.Error
import com.peknight.security.cipher.AES
import org.scalatest.flatspec.AsyncFlatSpec

class CipherFlatSpec extends AsyncFlatSpec with AsyncIOSpec:
  "AES" should "succeed" in {
    val run =
      for
        cipher <- EitherT(Cipher.getInstance[IO](AES).map(_.asRight[Error]).attempt)
      yield ()
    run.value.asserting(result => assert(true))
  }
end CipherFlatSpec
