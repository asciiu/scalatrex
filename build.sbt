import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.flow",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "BittrexClient",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.1.1",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.10"
    )
  )
