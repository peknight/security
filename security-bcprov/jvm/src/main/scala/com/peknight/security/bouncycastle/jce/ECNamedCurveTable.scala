package com.peknight.security.bouncycastle.jce

import cats.effect.Sync
import com.peknight.security.spec.ECGenParameterSpecName
import org.bouncycastle.jce.ECNamedCurveTable as JECNamedCurveTable
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec

object ECNamedCurveTable:
  def getParameterSpec[F[_]: Sync](name: ECGenParameterSpecName): F[Option[ECNamedCurveParameterSpec]] =
    Sync[F].blocking(Option(JECNamedCurveTable.getParameterSpec(name.parameterSpecName)))
end ECNamedCurveTable
