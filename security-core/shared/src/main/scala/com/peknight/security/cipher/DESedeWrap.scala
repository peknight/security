package com.peknight.security.cipher

import com.peknight.security.cipher.mode.{CipherAlgorithmMode, KW}
import com.peknight.security.cipher.padding.CipherAlgorithmPadding

trait DESedeWrap extends DESede:
  override val mode: CipherAlgorithmMode = KW
  override def /(mode: CipherAlgorithmMode): This = DESede(mode, padding)
  override def /(padding: CipherAlgorithmPadding): This = DESede(mode, padding)
  override def transformation: String = "DESedeWrap"
  override def algorithm: String = transformation
end DESedeWrap
object DESedeWrap extends DESedeWrap
