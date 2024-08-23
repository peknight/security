package com.peknight.security.syntax

import com.peknight.security.signature.calculateSignatureByteLength

import java.security.spec.ECParameterSpec

trait ECParameterSpecSyntax:
  extension (params: ECParameterSpec)
    def bitLength: Int = params.getOrder.bitLength()
    def signatureByteLength: Int = calculateSignatureByteLength(bitLength)
  end extension
end ECParameterSpecSyntax
object ECParameterSpecSyntax extends ECParameterSpecSyntax