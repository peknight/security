import com.peknight.build.gav.*
import com.peknight.build.sbt.*

commonSettings

lazy val security = (project in file("."))
  .settings(name := "security")
  .aggregate(
    securityCore.jvm,
    securityCore.js,
    securityHttp4s.jvm,
    securityHttp4s.js,
    securityBouncyCastleProvider.jvm,
    securityBouncyCastleProvider.js,
    securityBouncyCastlePkix.jvm,
    securityBouncyCastlePkix.js,
    securityOtp.jvm,
    securityOtp.js,
    securityInstances,
  )

lazy val securityCore = (crossProject(JVMPlatform, JSPlatform) in file("security-core"))
  .settings(name := "security-core")
  .settings(crossDependencies(
    typelevel.catsEffect,
    typelevel.catsParse,
    peknight.fs2,
    peknight.fs2.io,
    peknight.codec.base,
    peknight.validation,
    peknight.commons.time,
    peknight.cats,
  ))
  .settings(crossTestDependencies(
    scalaTest.flatSpec,
    typelevel.catsEffect.testingScalaTest,
  ))

lazy val securityHttp4s = (crossProject(JVMPlatform, JSPlatform) in file("security-http4s"))
  .dependsOn(securityCore)
  .settings(name := "security-http4s")
  .settings(crossDependencies(http4s))

lazy val securityBouncyCastleProvider = (crossProject(JVMPlatform, JSPlatform) in file("security-bcprov"))
  .dependsOn(securityCore)
  .settings(name := "security-bcprov")
  .settings(crossTestDependencies(
    scalaTest.flatSpec,
    typelevel.catsEffect.testingScalaTest,
  ))
  .jvmSettings(libraryDependencies ++= Seq(jvmDependency(bouncyCastle.provider)))

lazy val securityBouncyCastlePkix = (crossProject(JVMPlatform, JSPlatform) in file("security-bcpkix"))
  .dependsOn(securityCore)
  .settings(name := "security-bcpkix")
  .settings(crossDependencies(
    peknight.method,
    peknight.cats,
    fs2.io,
  ))
  .jvmSettings(libraryDependencies ++= Seq(jvmDependency(bouncyCastle.pkix)))

lazy val securityOtp = (crossProject(JVMPlatform, JSPlatform) in file("security-otp"))
  .dependsOn(securityCore)
  .settings(name := "security-otp")
  .settings(crossDependencies(
    peknight.cats,
    peknight.validation.spire,
  ))
  .settings(crossTestDependencies(
    scalaTest.flatSpec,
    typelevel.catsEffect.testingScalaTest,
  ))

lazy val securityInstances = (project in file("security-instances"))
  .settings(name := "security-instances")
  .aggregate(
    securityCodecInstances.jvm,
    securityCodecInstances.js,
  )

lazy val securityCodecInstances = (crossProject(JVMPlatform, JSPlatform) in file("security-instances/codec"))
  .dependsOn(securityCore)
  .settings(name := "security-codec-instances")
  .settings(crossDependencies(
    peknight.codec,
  ))
