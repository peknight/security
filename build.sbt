import com.peknight.build.gav.*
import com.peknight.build.sbt.*

commonSettings

lazy val security = (project in file("."))
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
  .settings(
    name := "security",
  )

lazy val securityCore = (crossProject(JVMPlatform, JSPlatform) in file("security-core"))
  .settings(crossDependencies(
    typelevel.catsEffect,
    typelevel.catsParse,
    peknight.ext.fs2,
    peknight.ext.fs2.io,
    peknight.codec.base,
    peknight.validation,
    peknight.commons.time,
    peknight.instances.cats.time,
  ))
  .settings(crossTestDependencies(
    scalaTest.flatSpec,
    typelevel.catsEffect.testingScalaTest,
  ))
  .settings(
    name := "security-core",
  )

lazy val securityHttp4s = (crossProject(JVMPlatform, JSPlatform) in file("security-http4s"))
  .dependsOn(securityCore)
  .settings(crossDependencies(http4s))
  .settings(
    name := "security-http4s",
  )

lazy val securityBouncyCastleProvider = (crossProject(JVMPlatform, JSPlatform) in file("security-bcprov"))
  .dependsOn(securityCore)
  .settings(crossTestDependencies(
    scalaTest.flatSpec,
    typelevel.catsEffect.testingScalaTest,
  ))
  .settings(
    name := "security-bcprov",
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      jvmDependency(bouncyCastle.provider),
    ),
  )

lazy val securityBouncyCastlePkix = (crossProject(JVMPlatform, JSPlatform) in file("security-bcpkix"))
  .dependsOn(securityCore)
  .settings(crossDependencies(
    peknight.method,
    peknight.ext.cats,
    fs2.io,
  ))
  .settings(
    name := "security-bcpkix",
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      jvmDependency(bouncyCastle.pkix),
    ),
  )

lazy val securityOtp = (crossProject(JVMPlatform, JSPlatform) in file("security-otp"))
  .dependsOn(securityCore)
  .settings(crossDependencies(
    peknight.ext.cats,
    peknight.validation.spire,
  ))
  .settings(crossTestDependencies(
    scalaTest.flatSpec,
    typelevel.catsEffect.testingScalaTest,
  ))
  .settings(
    name := "security-otp",
  )

lazy val securityInstances = (project in file("security-instances"))
  .aggregate(
    securityCodecInstances.jvm,
    securityCodecInstances.js,
  )
  .settings(
    name := "security-instances",
  )

lazy val securityCodecInstances = (crossProject(JVMPlatform, JSPlatform) in file("security-instances/codec"))
  .dependsOn(securityCore)
  .settings(crossDependencies(
    peknight.codec,
  ))
  .settings(
    name := "security-codec-instances",
  )
