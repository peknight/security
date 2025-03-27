package com.peknight.security.http4s.instances

import cats.data.EitherT
import cats.effect.Async
import cats.syntax.functor.*
import com.peknight.error.syntax.applicativeError.asError
import com.peknight.security.fs2.certificate.toX509Certificate
import com.peknight.security.http4s.media.MediaRange.`application/pem-certificate-chain`
import org.http4s.{EntityDecoder, MalformedMessageBodyFailure}

import java.security.cert.X509Certificate

trait X509CertificateInstances:
  given x509CertificatesEntityDecoder[F[_]: Async]: EntityDecoder[F, List[X509Certificate]] =
    EntityDecoder.decodeBy(`application/pem-certificate-chain`)(media =>
      EitherT(media.body.through(toX509Certificate[F]()).compile.toList.asError
        .map(_.left.map(error => MalformedMessageBodyFailure(error.message, error.throwable)))
      )
    )
end X509CertificateInstances
object X509CertificateInstances extends X509CertificateInstances
