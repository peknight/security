package com.peknight.security

import cats.effect.Sync
import cats.syntax.functor.*
import com.peknight.security.algorithm.{Algorithm, ServiceName}

import java.security.{Provider, Security as JSecurity}
import scala.jdk.CollectionConverters.*

object Security:
  def addProvider[F[_]: Sync](provider: Provider): F[Int] = Sync[F].blocking(JSecurity.addProvider(provider))
  def getAlgorithms[F[_]: Sync](serviceName: ServiceName): F[Set[String]] =
    Sync[F].blocking(JSecurity.getAlgorithms(serviceName.serviceName).asScala.toSet)
  def isAvailable[F[_]: Sync](serviceName: ServiceName, algorithm: Algorithm): F[Boolean] =
    getAlgorithms[F](serviceName).map(_.exists(_.equalsIgnoreCase(algorithm.algorithm)))
end Security
