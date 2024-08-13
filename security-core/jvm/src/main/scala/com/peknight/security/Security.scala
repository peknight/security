package com.peknight.security

import cats.effect.Sync
import com.peknight.security.algorithm.ServiceName

import java.security.{Provider, Security as JSecurity}
import scala.jdk.CollectionConverters.*

object Security:
  def addProvider[F[_]: Sync](provider: Provider): F[Int] = Sync[F].blocking(JSecurity.addProvider(provider))
  def getAlgorithms[F[_]: Sync](serviceName: ServiceName): F[Set[String]] =
    Sync[F].blocking(JSecurity.getAlgorithms(serviceName.serviceName).asScala.toSet)
end Security
