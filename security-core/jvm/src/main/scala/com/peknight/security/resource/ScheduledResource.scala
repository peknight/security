package com.peknight.security.resource

import cats.Monad
import cats.data.NonEmptyList
import cats.effect.{Async, Ref, Resource}
import cats.syntax.applicative.*
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import cats.syntax.option.none
import com.peknight.error.syntax.applicativeError.asError
import com.peknight.fs2.resource.ScheduledResource as Fs2ScheduledResource
import com.peknight.security.key.store.pkcs12
import com.peknight.security.provider.Provider
import com.peknight.security.syntax.x509Certificate.nearExpiry
import fs2.Stream

import java.security.cert.X509Certificate
import java.security.{KeyPair, KeyStore, Provider as JProvider}
import scala.concurrent.duration.FiniteDuration

object ScheduledResource:
  def apply[F[_]: Async, A](scheduler: Stream[F, ?], threshold: FiniteDuration, alias: String,
                            keyPassword: String, provider: Option[Provider | JProvider])
                           (initF: F[(NonEmptyList[X509Certificate], KeyPair)])
                           (nextF: F[(NonEmptyList[X509Certificate], KeyPair)])
                           (resourceF: (KeyStore, NonEmptyList[X509Certificate], KeyPair) => F[Resource[F, A]])
  : Resource[F, Ref[F, ((NonEmptyList[X509Certificate], KeyPair), A)]] =
    Fs2ScheduledResource[F, (NonEmptyList[X509Certificate], KeyPair), A](scheduler)(initF) {
      (certificateChain, keyPair) =>
        Monad[F].ifM(certificateChain.head.nearExpiry[F](threshold))(nextF.asError.map(_.toOption), none.pure[F])
    } { (certificateChain, keyPair) =>
      for
        keyStore <- pkcs12[F](alias, keyPair.getPrivate, keyPassword, certificateChain, provider)
        resource <- resourceF(keyStore, certificateChain, keyPair)
      yield
        resource
    }
end ScheduledResource
