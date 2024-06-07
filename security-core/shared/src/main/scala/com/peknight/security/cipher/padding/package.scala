package com.peknight.security.cipher

import com.peknight.security.digest.{MD5, `SHA-256`, `SHA-512`}
import com.peknight.security.mgf.MGF1

package object padding:
  val OAEPWithMD5AndMGF1Padding: OAEPWithDigestAndMGFPadding = OAEP.withDigestAndMGF(MD5, MGF1)
  val `OAEPWithSHA-256AndMGF1Padding`: OAEPWithDigestAndMGFPadding = OAEP.withDigestAndMGF(`SHA-256`, MGF1)
  val `OAEPWithSHA-512AndMGF1Padding`: OAEPWithDigestAndMGFPadding = OAEP.withDigestAndMGF(`SHA-512`, MGF1)
end padding
