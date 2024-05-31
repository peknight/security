package com.peknight.security.certificate.store

trait LDAP extends CertStoreType:
  def certStoreType: String = "LDAP"
end LDAP
object LDAP extends LDAP
