package com.peknight.security.certificate.revocation.list

/**
 * RFC5280
 */
enum ReasonCode(val code: Int) derives CanEqual:
  case unspecified extends ReasonCode(0)
  case keyCompromise extends ReasonCode(1)
  case cACompromise extends ReasonCode(2)
  case affiliationChanged extends ReasonCode(3)
  case superseded extends ReasonCode(4)
  case cessationOfOperation extends ReasonCode(5)
  case certificateHold extends ReasonCode(6)
  // value 7 is not used
  case removeFromCRL extends ReasonCode(8)
  case privilegeWithdrawn extends ReasonCode(9)
  case aACompromise extends ReasonCode(10)
end ReasonCode
