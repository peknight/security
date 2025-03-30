package com.peknight.security.bouncycastle.openssl

import cats.effect.Sync
import fs2.io.file.Path
import org.bouncycastle.openssl.PEMParser as JPEMParser

import java.io.{FileReader, Reader as JReader}

object PEMParser:
  def apply[F[_]: Sync](path: Path): F[JPEMParser] =
    Sync[F].blocking(new JPEMParser(new FileReader(path.toNioPath.toFile)))

  def apply[F[_]: Sync](reader: JReader): F[JPEMParser] =
    Sync[F].blocking(new JPEMParser(reader))
end PEMParser

