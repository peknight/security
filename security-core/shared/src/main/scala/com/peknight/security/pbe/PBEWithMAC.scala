package com.peknight.security.pbe

import com.peknight.security.mac.MACAlgorithm

trait PBEWithMAC extends MACAlgorithm:
  def mac: MACAlgorithm
  def algorithm: String = s"PBEWith${mac.algorithm}"
end PBEWithMAC
object PBEWithMAC:
  private case class PBEWithMAC(mac: MACAlgorithm) extends com.peknight.security.pbe.PBEWithMAC
  def apply(mac: MACAlgorithm): com.peknight.security.pbe.PBEWithMAC = PBEWithMAC(mac)
end PBEWithMAC

