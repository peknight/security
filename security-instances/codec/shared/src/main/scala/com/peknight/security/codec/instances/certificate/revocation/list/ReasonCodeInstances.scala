package com.peknight.security.codec.instances.certificate.revocation.list

import com.peknight.codec.instances.NumberCodecInstances
import com.peknight.codec.number.Number
import com.peknight.security.certificate.revocation.list.ReasonCode

trait ReasonCodeInstances extends NumberCodecInstances[ReasonCode]:
  def toNumber(t: ReasonCode): Number = Number.fromInt(t.code)
  def fromNumber(n: Number): Option[ReasonCode] = n.toInt.flatMap(n => ReasonCode.values.find(_.code == n))
end ReasonCodeInstances
object ReasonCodeInstances extends ReasonCodeInstances
