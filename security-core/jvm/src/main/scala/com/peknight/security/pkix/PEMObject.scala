package com.peknight.security.pkix

import com.peknight.codec.obj.Object
import com.peknight.codec.base.Base64

case class PEMObject(label: String, data: Base64, headers: Object[String])
