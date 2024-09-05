package com.peknight.security.cipher

enum WrappedKeyType(val keyType: Int):
  case PublicKey extends WrappedKeyType(1)
  case PrivateKey extends WrappedKeyType(2)
  case SecretKey extends WrappedKeyType(3)
end WrappedKeyType
