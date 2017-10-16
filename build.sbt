name := "BittrexClient"

version := "1.0"

scalaVersion := "2.12.3"

val http4sVersion = "0.17.0-M3"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "org.http4s"            %% "http4s-dsl"             % http4sVersion,
  "org.http4s"            %% "http4s-blaze-server"    % http4sVersion,
  "org.http4s"            %% "http4s-blaze-client"    % http4sVersion,
  "org.http4s"            %% "http4s-circe"           % http4sVersion,
  "com.github.pureconfig" %% "pureconfig"             % "0.8.0",
  "com.pauldijou"         %% "jwt-core"               % "0.14.0",
  "com.pauldijou"         %% "jwt-circe"              % "0.14.0",
  "io.circe"              %% "circe-generic"          % "0.8.0",
  "io.circe"              %% "circe-literal"          % "0.8.0" ,
  "com.typesafe.play"     %% "play-ahc-ws-standalone" % "1.1.1",
  "com.typesafe.akka"     %% "akka-http-spray-json"   % "10.0.10",
  "com.roundeights"       %% "hasher"                 % "1.2.0",
  "ch.qos.logback"        %  "logback-classic"        % "1.2.1",
  "org.scalacheck"        %% "scalacheck"             % "1.13.4" % "test",
  "org.specs2"            %% "specs2-core"            % "3.9.1"  % "test",
  "org.specs2"            %% "specs2-mock"            % "3.9.1"  % "test"
)

scalacOptions in Test ++= Seq("-Yrangepos")

