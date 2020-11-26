import Dependencies._

ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "1.0.0-SNAPSHOT"
ThisBuild / organization := "com.home"
ThisBuild / organizationName := "artem"

lazy val http4sLibs = Seq(
  Libraries.http4sCore,
  Libraries.http4sDsl,
  Libraries.http4sBlazeClient,
  Libraries.http4sBlazeServer,
  Libraries.http4sCirce,
  Libraries.http4sJawnFs2
)

lazy val circeLibs = Seq(
  Libraries.circeCore,
  Libraries.circeParser,
  Libraries.circeGeneric,
  Libraries.circeDerivation,
  Libraries.circeGenericExtras,
  Libraries.circeShapes
)

lazy val root = (project in file("."))
  .settings(
    name := "pfp-with-scala",
    scalacOptions += "-Ymacro-annotations",
    scalafmtOnCompile := true,
    libraryDependencies ++= Seq(
      CompilerPlugins.betterMonadicFor,
      CompilerPlugins.contextApplied,
      CompilerPlugins.kindProjector,
      Libraries.cats,
      Libraries.catsEffect,
      Libraries.catsMeowMtlCore,
      Libraries.catsMeowMtlEffects,
      Libraries.console4cats,
      Libraries.derevoCats,
      Libraries.derevoTagless,
      Libraries.fs2,
      Libraries.monocleCore,
      Libraries.monocleMacro,
      Libraries.newtype,
      Libraries.refinedCore
    ) ++ http4sLibs ++ circeLibs
  )
