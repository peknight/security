package com.peknight.security.syntax

import com.peknight.security.error.SecurityError
import com.peknight.security.signature.EdDSA
import scodec.bits.ByteVector

import java.security.interfaces.{EdECKey, EdECPrivateKey, EdECPublicKey}

trait EdECKeySyntax:
  extension (key: EdECKey)
    def getParameterSpecName: Either[SecurityError, EdDSA] = EdDSA.getParameterSpecName(key)
  end extension
  extension (publicKey: EdECPublicKey)
    def rawPublicKey: Either[SecurityError, ByteVector] = EdDSA.rawPublicKey(publicKey)
  end extension
  extension (privateKey: EdECPrivateKey)
    def rawPrivateKey: ByteVector = EdDSA.rawPrivateKey(privateKey)
  end extension
end EdECKeySyntax
object EdECKeySyntax extends EdECKeySyntax
