ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

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
      "org.scodec" %% "scodec-bits" % scodecVersion,
    ),
  )

val catsEffectVersion = "3.5.4"
val scodecVersion = "1.2.0"
