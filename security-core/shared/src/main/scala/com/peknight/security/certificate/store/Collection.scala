package com.peknight.security.certificate.store

trait Collection extends CertStoreType:
  def certStoreType: String = "Collection"
end Collection
object Collection extends Collection
