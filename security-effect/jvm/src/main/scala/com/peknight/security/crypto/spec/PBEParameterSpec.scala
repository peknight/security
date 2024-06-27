package com.peknight.security.crypto.spec

import scodec.bits.ByteVector

import java.security.spec.AlgorithmParameterSpec
import javax.crypto.spec.PBEParameterSpec as JPBEParameterSpec

object PBEParameterSpec:
  def apply(salt: ByteVector, iterationCount: Int): JPBEParameterSpec =
    JPBEParameterSpec(salt.toArray, iterationCount)
  def apply(salt: ByteVector, iterationCount: Int, paramSpec: AlgorithmParameterSpec): JPBEParameterSpec =
    JPBEParameterSpec(salt.toArray, iterationCount, paramSpec)
end PBEParameterSpec
