package com.peknight.security.http4s.media

import org.http4s.MediaType

object MediaRange:
  val `application/pem-certificate-chain`: MediaType = MediaType.unsafeParse("application/pem-certificate-chain")
end MediaRange
