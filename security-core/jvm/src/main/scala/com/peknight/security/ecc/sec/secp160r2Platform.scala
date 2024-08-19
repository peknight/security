package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldFp, ECParameterSpec, ECPoint, EllipticCurve}

trait secp160r2Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldFp(new BigInteger("1461501637330902918203684832716283019651637554291")),
        new BigInteger("1461501637330902918203684832716283019651637554288"),
        new BigInteger("1032640608390511495214075079957864673410201913530")
      ),
      new ECPoint(
        new BigInteger("473058756663038503608844550604547710019657059949"),
        new BigInteger("1454008495369951658060798698479395908327453245230")
      ),
      new BigInteger("1461501637330902918203685083571792140653176136043"),
      1
    )
end secp160r2Platform

