package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldF2m, ECParameterSpec, ECPoint, EllipticCurve}

trait sect193r2Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldF2m(193, new BigInteger("12554203470773361527671578846415332832204710888928069058561")),
        new BigInteger("8727883239842844933732220251183800395075376381527050055835"),
        new BigInteger("4946476016329916785216072668156805272487483199688540362414")
      ),
      new ECPoint(
        new BigInteger("5338303459516340642470631733027504050881957481257426321039"),
        new BigInteger("11342401828932489703393760444197037534175341100765106065260")
      ),
      new BigInteger("6277101735386680763835789423314955362437298222279840143829"),
      2
    )
end sect193r2Platform

