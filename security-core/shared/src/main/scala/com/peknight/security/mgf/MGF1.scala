package com.peknight.security.mgf

trait MGF1 extends MGF with MGF1Platform:
  val mgf: String = "MGF1"
end MGF1
object MGF1 extends MGF1
