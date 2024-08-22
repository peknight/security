package com.peknight.security.syntax

import com.peknight.security.ecc.EC
import scodec.bits.ByteVector

import java.security.interfaces.{ECKey, ECPrivateKey, ECPublicKey}

trait ECKeySyntax:
  extension (key: ECKey)
    def minByteLength: Int = EC.minByteLength(key)
  end extension
  extension (publicKey: ECPublicKey)
    def rawXCoordinate: ByteVector = EC.rawXCoordinate(publicKey)
    def rawYCoordinate: ByteVector = EC.rawYCoordinate(publicKey)
    def rawPoint: (ByteVector, ByteVector) = EC.rawPoint(publicKey)
  end extension
  extension (privateKey: ECPrivateKey)
    def rawPrivateKey: ByteVector = EC.rawPrivateKey(privateKey)
  end extension
end ECKeySyntax
object ECKeySyntax extends ECKeySyntax
