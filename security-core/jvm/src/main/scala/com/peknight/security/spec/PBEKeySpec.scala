package com.peknight.security.spec

import scodec.bits.ByteVector

import javax.crypto.spec.PBEKeySpec as JPBEKeySpec

object PBEKeySpec:
  def apply(password: String): JPBEKeySpec = JPBEKeySpec(password.toCharArray)
  def apply(password: String, salt: ByteVector, iterationCount: Int, keyLength: Int): JPBEKeySpec =
    JPBEKeySpec(password.toCharArray, salt.toArray, iterationCount, keyLength)
  def apply(password: String, salt: ByteVector, iterationCount: Int): JPBEKeySpec =
    JPBEKeySpec(password.toCharArray, salt.toArray, iterationCount)
end PBEKeySpec
