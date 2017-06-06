crossScalaVersions := Seq("2.10.6", "2.11.11", "2.12.2")

lazy val root = project
  .in(file("."))
  .aggregate(squantsFxJS, squantsFxJVM)

lazy val squantsFx = crossProject
  .in(file("."))
  .settings(
    scalacOptions ++= Seq("-deprecation", "-feature", "-Xlint"),
    name := "squants-fx",
    version := "0.0.1",
    scalaVersion in ThisBuild := "2.12.2",
    organization := "my.will.be.done",
    libraryDependencies ++= {
      val circeVersion = "0.8.0"
      Seq(
        "fr.hmil"       %%% "roshttp"       % "2.0.2",
        "org.typelevel" %%% "squants"       % "1.3.0",
        "io.circe"      %%% "circe-generic" % circeVersion,
        "io.circe"      %%% "circe-parser"  % circeVersion
      )
    }
  )

lazy val squantsFxJVM = squantsFx.jvm
lazy val squantsFxJS  = squantsFx.js
