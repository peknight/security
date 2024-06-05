package com.peknight.security.crypto.syntax

import cats.effect.Sync
import com.peknight.security.cipher.Opmode
import scodec.bits.ByteVector

import java.security.Key
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.Cipher

trait CipherSyntax:
  extension (cipher: Cipher)
    def initF[F[_]: Sync](opmode: Opmode, key: Key): F[Unit] =
      cipher.getBlockSize
      Sync[F].delay(cipher.init(opmode.mode, key))
    def initF[F[_]: Sync](opmode: Opmode, key: Key, params: AlgorithmParameterSpec): F[Unit] =
      Sync[F].delay(cipher.init(opmode.mode, key, params))
    def doFinalF[F[_]: Sync](input: ByteVector): F[ByteVector] =
      Sync[F].delay(ByteVector(cipher.doFinal(input.toArray)))
  end extension
end CipherSyntax
object CipherSyntax extends CipherSyntax
