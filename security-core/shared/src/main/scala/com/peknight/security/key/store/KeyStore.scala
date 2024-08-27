package com.peknight.security.key.store

import com.peknight.security.algorithm.ServiceName

object KeyStore extends ServiceName with KeyStoreCompanion:
  def serviceName: String = "KeyStore"
end KeyStore

