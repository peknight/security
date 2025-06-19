ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.1"

ThisBuild / organization := "com.peknight"

ThisBuild / publishTo := {
  val nexus = "https://nexus.peknight.com/repository"
  if (isSnapshot.value)
    Some("snapshot" at s"$nexus/maven-snapshots/")
  else
    Some("releases" at s"$nexus/maven-releases/")
}

ThisBuild / credentials ++= Seq(
  Credentials(Path.userHome / ".sbt" / ".credentials")
)

ThisBuild / resolvers ++= Seq(
  "Pek Nexus" at "https://nexus.peknight.com/repository/maven-public/",
)

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
  .settings(commonSettings)
  .settings(
    name := "security",
  )

lazy val securityCore = (crossProject(JSPlatform, JVMPlatform) in file("security-core"))
  .settings(commonSettings)
  .settings(
    name := "security-core",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-effect" % catsEffectVersion,
      "org.typelevel" %%% "cats-parse" % catsParseVersion,
      "com.peknight" %%% "fs2-ext" % pekExtVersion,
      "com.peknight" %%% "fs2-io-ext" % pekExtVersion,
      "com.peknight" %%% "codec-base" % pekCodecVersion,
      "com.peknight" %%% "validation-core" % pekValidationVersion,
      "com.peknight" %%% "commons-time" % pekCommonsVersion,
      "com.peknight" %%% "cats-instances-time" % pekInstancesVersion,
      "org.scalatest" %% "scalatest-flatspec" % scalaTestVersion % Test,
      "org.typelevel" %%% "cats-effect-testing-scalatest" % catsEffectTestingScalaTestVersion % Test,
    ),
  )

lazy val securityHttp4s = (crossProject(JSPlatform, JVMPlatform) in file("security-http4s"))
  .dependsOn(securityCore)
  .settings(commonSettings)
  .settings(
    name := "security-http4s",
    libraryDependencies ++= Seq(
      "org.http4s" %%% "http4s-core" % http4sVersion,
    ),
  )

lazy val securityBouncyCastleProvider = (crossProject(JSPlatform, JVMPlatform) in file("security-bcprov"))
  .dependsOn(
    securityCore,
  )
  .settings(commonSettings)
  .settings(
    name := "security-bcprov",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest-flatspec" % scalaTestVersion % Test,
      "org.typelevel" %%% "cats-effect-testing-scalatest" % catsEffectTestingScalaTestVersion % Test,
    ),
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      bouncyCastleProvider,
    ),
  )

lazy val securityBouncyCastlePkix = (crossProject(JSPlatform, JVMPlatform) in file("security-bcpkix"))
  .dependsOn(
    securityCore,
  )
  .settings(commonSettings)
  .settings(
    name := "security-bcpkix",
    libraryDependencies ++= Seq(
      "com.peknight" %%% "method-core" % pekMethodVersion,
      "com.peknight" %%% "cats-ext" % pekExtVersion,
      "co.fs2" %%% "fs2-io" % fs2Version,
    ),
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      bouncyCastlePkix,
    ),
  )

lazy val securityOtp = (crossProject(JSPlatform, JVMPlatform) in file("security-otp"))
  .dependsOn(securityCore)
  .settings(commonSettings)
  .settings(
    name := "security-otp",
    libraryDependencies ++= Seq(
      "com.peknight" %%% "cats-ext" % pekExtVersion,
      "com.peknight" %%% "validation-spire" % pekValidationVersion,
      "org.scalatest" %% "scalatest-flatspec" % scalaTestVersion % Test,
      "org.typelevel" %%% "cats-effect-testing-scalatest" % catsEffectTestingScalaTestVersion % Test,
    )
  )

lazy val securityInstances = (project in file("security-instances"))
  .aggregate(
    securityCodecInstances.jvm,
    securityCodecInstances.js,
  )
  .settings(commonSettings)
  .settings(
    name := "security-instances",
    libraryDependencies ++= Seq(
    ),
  )

lazy val securityCodecInstances = (crossProject(JSPlatform, JVMPlatform) in file("security-instances/codec"))
  .dependsOn(securityCore)
  .settings(commonSettings)
  .settings(
    name := "security-codec-instances",
    libraryDependencies ++= Seq(
      "com.peknight" %%% "codec-core" % pekCodecVersion,
    ),
  )

val catsParseVersion = "0.3.10"
val catsEffectVersion = "3.6.1"
val fs2Version = "3.12.0"
val http4sVersion = "1.0.0-M34"
val bouncyCastleVersion = "1.81"
val scalaTestVersion = "3.2.19"
val catsEffectTestingScalaTestVersion = "1.6.0"

val pekVersion = "0.1.0-SNAPSHOT"
val pekCommonsVersion = pekVersion
val pekExtVersion = pekVersion
val pekInstancesVersion = pekVersion
val pekValidationVersion = pekVersion
val pekMethodVersion = pekVersion
val pekCodecVersion = pekVersion

val bouncyCastleProvider = "org.bouncycastle" % "bcprov-jdk18on" % bouncyCastleVersion
val bouncyCastlePkix = "org.bouncycastle" % "bcpkix-jdk18on" % bouncyCastleVersion
