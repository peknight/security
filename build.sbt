ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.2"

ThisBuild / organization := "com.peknight"

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-unchecked",
    "-Xfatal-warnings",
    "-language:strictEquality",
    "-Xmax-inlines:64"
  ),
)

lazy val security = (project in file("."))
  .aggregate(
    securityCore.jvm,
    securityCore.js,
    securityEffect.jvm,
    securityEffect.js,
    securityBouncyCastleProvider.jvm,
    securityBouncyCastleProvider.js,
    securityBouncyCastlePkix.jvm,
    securityBouncyCastlePkix.js,
  )
  .settings(commonSettings)
  .settings(
    name := "security",
  )

lazy val securityCore = (crossProject(JSPlatform, JVMPlatform) in file("security-core"))
  .settings(commonSettings)
  .settings(
    name := "security-core",
    libraryDependencies ++= Seq(
    ),
  )

lazy val securityEffect = (crossProject(JSPlatform, JVMPlatform) in file("security-effect"))
  .dependsOn(securityCore)
  .settings(commonSettings)
  .settings(
    name := "security-core",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-effect" % catsEffectVersion,
      "org.scodec" %%% "scodec-bits" % scodecVersion,
      "com.peknight" %%% "error-core" % pekErrorVersion % Test,
      "org.typelevel" %%% "cats-effect-testing-scalatest" % catsEffectTestingScalaTestVersion % Test,
    ),
  )

lazy val securityBouncyCastleProvider = (crossProject(JSPlatform, JVMPlatform) in file("security-bcprov"))
  .dependsOn(securityCore)
  .settings(commonSettings)
  .settings(
    name := "security-bcprov",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-effect" % catsEffectVersion,
    ),
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      bouncyCastleProvider,
    ),
  )

lazy val securityBouncyCastlePkix = (crossProject(JSPlatform, JVMPlatform) in file("security-bcpkix"))
  .settings(commonSettings)
  .settings(
    name := "security-bcpkix",
    libraryDependencies ++= Seq(
      "co.fs2" %%% "fs2-io" % fs2Version,
    ),
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      bouncyCastlePkix,
    ),
  )

val catsEffectVersion = "3.5.4"
val scodecVersion = "1.2.0"
val fs2Version = "3.10.2"
val bouncyCastleVersion = "1.78.1"
val pekVersion = "0.1.0-SNAPSHOT"
val pekErrorVersion = pekVersion
val catsEffectTestingScalaTestVersion = "1.5.0"

val bouncyCastleProvider = "org.bouncycastle" % "bcprov-jdk18on" % bouncyCastleVersion
val bouncyCastlePkix = "org.bouncycastle" % "bcpkix-jdk18on" % bouncyCastleVersion
