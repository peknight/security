package com.peknight.security.bouncycastle.openssl.jcajce

import cats.effect.{Resource, Sync}
import cats.syntax.applicativeError.*
import fs2.io.file.Path
import org.bouncycastle.openssl.jcajce.JcaPEMWriter as JJcaPEMWriter

import java.io.{FileWriter, Writer as JWriter}

object JcaPEMWriter:
  def apply[F[_] : Sync](path: Path): Resource[F, JJcaPEMWriter] =
    Resource.fromAutoCloseable[F, JJcaPEMWriter](
      Sync[F].blocking(new JJcaPEMWriter(new FileWriter(path.toNioPath.toFile)))
    )

  def handleReleaseErrorWith[F[_] : Sync](path: Path)(f: Throwable => F[Unit])
  : Resource[F, JJcaPEMWriter] =
    Resource.make[F, JJcaPEMWriter](Sync[F].blocking(new JJcaPEMWriter(new FileWriter(path.toNioPath.toFile))))(
      closeable => Sync[F].blocking(closeable.close()).handleErrorWith(f)
    )

  def apply[F[_]: Sync](writer: JWriter): Resource[F, JJcaPEMWriter] =
    Resource.fromAutoCloseable[F, JJcaPEMWriter](Sync[F].blocking(new JJcaPEMWriter(writer)))

  def handleReleaseErrorWith[F[_]: Sync](writer: JWriter)(f: Throwable => F[Unit]): Resource[F, JJcaPEMWriter] =
    Resource.make[F, JJcaPEMWriter](Sync[F].blocking(new JJcaPEMWriter(writer)))(closeable =>
      Sync[F].blocking(closeable.close()).handleErrorWith(f)
    )
end JcaPEMWriter
