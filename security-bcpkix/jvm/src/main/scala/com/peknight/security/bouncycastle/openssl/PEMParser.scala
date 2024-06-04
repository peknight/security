package com.peknight.security.bouncycastle.openssl

import cats.effect.{Resource, Sync}
import cats.syntax.applicativeError.*
import fs2.io.file.Path
import org.bouncycastle.openssl.PEMParser as JPEMParser

import java.io.{FileReader, Reader as JReader}

object PEMParser:

  def apply[F[_] : Sync](path: Path): Resource[F, JPEMParser] =
    Resource.fromAutoCloseable[F, JPEMParser](Sync[F].blocking(new JPEMParser(new FileReader(path.toNioPath.toFile))))

  def handleReleaseErrorWith[F[_] : Sync](path: Path)(f: Throwable => F[Unit]): Resource[F, JPEMParser] =
    Resource.make[F, JPEMParser](Sync[F].blocking(new JPEMParser(new FileReader(path.toNioPath.toFile))))(closeable =>
      Sync[F].blocking(closeable.close()).handleErrorWith(f)
    )

  def apply[F[_]: Sync](reader: JReader): Resource[F, JPEMParser] =
    Resource.fromAutoCloseable[F, JPEMParser](Sync[F].blocking(new JPEMParser(reader)))

  def handleReleaseErrorWith[F[_]: Sync](reader: JReader)(f: Throwable => F[Unit]): Resource[F, JPEMParser] =
    Resource.make[F, JPEMParser](Sync[F].blocking(new JPEMParser(reader)))(
      closeable => Sync[F].blocking(closeable.close()).handleErrorWith(f)
    )
end PEMParser

