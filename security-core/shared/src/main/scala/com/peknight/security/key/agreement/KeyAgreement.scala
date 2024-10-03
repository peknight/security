package com.peknight.security.key.agreement

import com.peknight.security.algorithm.ServiceName

object KeyAgreement extends ServiceName with KeyAgreementCompanion:
  def serviceName: String = "KeyAgreement"
end KeyAgreement
