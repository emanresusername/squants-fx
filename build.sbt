crossScalaVersions := Seq("2.10.6", "2.11.11", "2.12.2")

lazy val root = project
  .in(file("."))
  .aggregate(squantsExchangeRatesJS, squantsExchangeRatesJVM)

lazy val squantsExchangeRates = crossProject
  .in(file("."))
  .settings(
    scalacOptions ++= Seq("-deprecation", "-feature", "-Xlint"),
    name := "squants-exchange-rates",
    version := "0.0.1",
    scalaVersion in ThisBuild := "2.12.2",
    organization := "my.will.be.done",
    libraryDependencies ++= {
      val circeVersion = "0.8.0"
      Seq(
        "io.monix"      %%% "monix"         % "2.3.0",
        "fr.hmil"       %%% "roshttp"       % "2.0.1",
        "org.typelevel" %%% "squants"       % "1.3.0",
        "io.circe"      %%% "circe-generic" % circeVersion,
        "io.circe"      %%% "circe-parser"  % circeVersion
      )
    }
  )

lazy val squantsExchangeRatesJVM = squantsExchangeRates.jvm
lazy val squantsExchangeRatesJS  = squantsExchangeRates.js
