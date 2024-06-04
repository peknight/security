package com.peknight.security.cipher

enum Opmode(val mode: Int):
  case Encrypt extends Opmode(1)
  case Decrypt extends Opmode(2)
  case Wrap extends Opmode(3)
  case Unwrap extends Opmode(4)
end Opmode
