package com.peknight.security.bouncycastle.openssl.jcajce

import cats.effect.Sync
import fs2.io.file.Path
import org.bouncycastle.openssl.jcajce.JcaPEMWriter as JJcaPEMWriter

import java.io.{FileWriter, Writer as JWriter}

object JcaPEMWriter:
  def apply[F[_]: Sync](path: Path): F[JJcaPEMWriter] =
    Sync[F].blocking(new JJcaPEMWriter(new FileWriter(path.toNioPath.toFile)))

  def apply[F[_]: Sync](writer: JWriter): F[JJcaPEMWriter] =
    Sync[F].blocking(new JJcaPEMWriter(writer))
end JcaPEMWriter
