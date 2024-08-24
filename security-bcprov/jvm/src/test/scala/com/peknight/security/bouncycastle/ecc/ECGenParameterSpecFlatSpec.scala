package com.peknight.security.bouncycastle.ecc

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import cats.syntax.traverse.*
import com.peknight.security.Security
import com.peknight.security.bouncycastle.jce.ECNamedCurveTable
import com.peknight.security.bouncycastle.jce.provider.BouncyCastleProvider
import com.peknight.security.ecc.EC
import com.peknight.security.ecc.brainpool.{Brainpool, brainpoolP256r1, brainpoolP384r1, brainpoolP512r1}
import com.peknight.security.ecc.sec.*
import com.peknight.security.spec.ECGenParameterSpecName
import com.peknight.security.syntax.ecParameterSpec.{bitLength, signatureByteLength}
import org.scalatest.flatspec.AsyncFlatSpec
import scodec.bits.ByteVector

import java.security.interfaces.ECPublicKey
import java.security.spec.{ECFieldF2m, ECFieldFp, ECParameterSpec}

class ECGenParameterSpecFlatSpec extends AsyncFlatSpec with AsyncIOSpec:
  private val curves =
    List(secp160k1, secp160r1, secp160r2, secp192k1, secp192r1, secp224k1, secp224r1, secp256k1, secp256r1, secp384r1,
      secp521r1, sect163k1, sect163r1, sect163r2, sect193r1, sect193r2, sect233k1, sect233r1, sect239k1, sect283k1,
      sect283r1, sect409k1, sect409r1, sect571k1, sect571r1, brainpoolP256r1, brainpoolP384r1, brainpoolP512r1
    )

  "ECGenParameterSpecFlatSpec" should "succeed" in {
    val run =
      for
        bouncyCastleProvider <- BouncyCastleProvider[IO]
        _ <- Security.addProvider[IO](bouncyCastleProvider)
        bcs <- curves.map { curve =>
          for
            spec <- ECNamedCurveTable.getParameterSpec[IO](curve)
            keyPair <- EC.paramsGenerateKeyPair[IO](spec, provider = Some(BouncyCastleProvider))
            params = keyPair.getPublic.asInstanceOf[ECPublicKey].getParams
          yield
            params.getCurve.equals(curve.ecParameterSpec.getCurve) &&
              params.getGenerator.equals(curve.ecParameterSpec.getGenerator) &&
              params.getOrder.equals(curve.ecParameterSpec.getOrder) &&
              params.getCofactor.equals(curve.ecParameterSpec.getCofactor)
        }.sequence.map(_.forall(identity))
        peks <- curves.map { curve =>
          for
            keyPair <- curve.generateKeyPair[IO](provider = Some(BouncyCastleProvider))
            params = keyPair.getPublic.asInstanceOf[ECPublicKey].getParams
          yield
            params.getCurve.equals(curve.ecParameterSpec.getCurve) &&
              params.getGenerator.equals(curve.ecParameterSpec.getGenerator) &&
              params.getOrder.equals(curve.ecParameterSpec.getOrder) &&
              params.getCofactor.equals(curve.ecParameterSpec.getCofactor) &&
              params.bitLength == curve.paramsBitLength &&
              params.signatureByteLength == curve.paramsSignatureByteLength
        }.sequence.map(_.forall(identity))
      yield bcs && peks
    run.asserting(assert)
  }

  def printECParameterSpec(name: ECGenParameterSpecName, ecParameterSpec: ECParameterSpec): String =
    val curve = ecParameterSpec.getCurve
    val generator = ecParameterSpec.getGenerator
    val order = ecParameterSpec.getOrder
    val cofactor = ecParameterSpec.getCofactor
    val fileName = s"${name.parameterSpecName}Platform"
    val pack = name match
      case _: Brainpool => "brainpool"
      case _: StandardsForEfficientCryptography => "sec"
    val jvmPath = s"security-core/jvm/src/main/scala/com/peknight/security/ecc/$pack/$fileName.scala"
    val jsPath = s"security-core/js/src/main/scala/com/peknight/security/ecc/$pack/$fileName.scala"
    val fieldClass =
      curve.getField match
        case _: ECFieldF2m => "ECFieldF2m"
        case _: ECFieldFp => "ECFieldFp"
    val fieldString =
      curve.getField match
        case f2m: ECFieldF2m => s"""new ECFieldF2m(${f2m.getM}, new BigInteger("${f2m.getReductionPolynomial}"))"""
        case fp: ECFieldFp => s"""new ECFieldFp(new BigInteger("${fp.getP}"))"""
    s"""
       |echo 'package com.peknight.security.ecc.$pack' >> $jvmPath
       |echo '' >> $jvmPath
       |echo 'import com.peknight.security.spec.ECParameterSpecPlatform' >> $jvmPath
       |echo '' >> $jvmPath
       |echo 'import java.math.BigInteger' >> $jvmPath
       |echo 'import java.security.spec.{$fieldClass, ECParameterSpec, ECPoint, EllipticCurve}' >> $jvmPath
       |echo '' >> $jvmPath
       |echo 'trait $fileName extends ECParameterSpecPlatform:' >> $jvmPath
       |echo '  def ecParameterSpec: ECParameterSpec =' >> $jvmPath
       |echo '    new ECParameterSpec(' >> $jvmPath
       |echo '      new EllipticCurve(' >> $jvmPath
       |echo '        $fieldString,' >> $jvmPath
       |echo '        new BigInteger("${curve.getA}"),' >> $jvmPath
       |echo '        new BigInteger("${curve.getB}")' >> $jvmPath
       |echo '      ),' >> $jvmPath
       |echo '      new ECPoint(' >> $jvmPath
       |echo '        new BigInteger("${generator.getAffineX}"),' >> $jvmPath
       |echo '        new BigInteger("${generator.getAffineY}")' >> $jvmPath
       |echo '      ),' >> $jvmPath
       |echo '      new BigInteger("$order"),' >> $jvmPath
       |echo '      $cofactor' >> $jvmPath
       |echo '    )' >> $jvmPath
       |echo 'end $fileName' >> $jvmPath
       |echo '' >> $jvmPath
    """.stripMargin

end ECGenParameterSpecFlatSpec
