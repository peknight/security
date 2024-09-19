package com.peknight.security.otp

import cats.data.EitherT
import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import com.peknight.cats.ext.syntax.eitherT.frLiftET
import com.peknight.codec.base.Base32
import com.peknight.error.Error
import com.peknight.security.random.SecureRandom
import com.peknight.security.syntax.secureRandom.nextBytesF
import org.scalatest.flatspec.AsyncFlatSpec

class OneTimePasswordFlatSpec extends AsyncFlatSpec with AsyncIOSpec:
  "OneTimePassword" should "succeed" in {
    val run =
      for
        secureRandom <- SecureRandom.getInstanceStrong[IO].frLiftET[Error]
        key <- secureRandom.nextBytesF[IO](8).frLiftET[Error]
        base32 = Base32.fromByteVector(key)
        oneTimePassword <- EitherT(OneTimePassword.generate[IO](base32))
        verified <- EitherT(OneTimePassword.verify[IO](base32, oneTimePassword))
      yield
        verified
    run.value.map(_.getOrElse(false)).asserting(assert)
  }
end OneTimePasswordFlatSpec
