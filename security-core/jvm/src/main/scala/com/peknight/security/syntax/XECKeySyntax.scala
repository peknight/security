package com.peknight.security.syntax

import com.peknight.security.error.SecurityError
import com.peknight.security.key.agreement.XDH
import scodec.bits.ByteVector

import java.security.interfaces.{XECKey, XECPrivateKey, XECPublicKey}

trait XECKeySyntax:
  extension (key: XECKey)
    def getParameterSpecName: Either[SecurityError, XDH] = XDH.getParameterSpecName(key)
  end extension
  extension (publicKey: XECPublicKey)
    def rawPublicKey: Either[SecurityError, ByteVector] = XDH.rawPublicKey(publicKey)
  end extension
  extension (privateKey: XECPrivateKey)
    def rawPrivateKey: ByteVector = XDH.rawPrivateKey(privateKey)
  end extension
end XECKeySyntax
object XECKeySyntax extends XECKeySyntax
