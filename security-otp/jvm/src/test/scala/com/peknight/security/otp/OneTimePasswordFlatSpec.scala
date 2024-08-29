package com.peknight.security.otp

import cats.data.EitherT
import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import cats.syntax.either.*
import com.peknight.codec.base.Base32
import com.peknight.security.random.SecureRandom
import com.peknight.security.syntax.secureRandom.nextBytesF
import org.scalatest.flatspec.AsyncFlatSpec

class OneTimePasswordFlatSpec extends AsyncFlatSpec with AsyncIOSpec:
  "OneTimePassword" should "succeed" in {
    val run =
      for
        secureRandom <- EitherT(SecureRandom.getInstanceStrong[IO].map(_.asRight))
        key <- EitherT(secureRandom.nextBytesF[IO](8).map(_.asRight))
        base32 = Base32.fromByteVector(key)
        oneTimePassword <- EitherT(OneTimePassword.generate[IO](base32))
        verified <- EitherT(OneTimePassword.verify[IO](base32, oneTimePassword))
      yield
        verified
    run.value.map(_.getOrElse(false)).asserting(assert)
  }
end OneTimePasswordFlatSpec
