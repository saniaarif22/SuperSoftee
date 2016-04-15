name := "SuperSoftee"
version := "0.2"
scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaCore,
  javaJdbc,
  "junit" % "junit" % "4.8",
  "uk.co.panaxiom" %% "play-jongo" % "0.9.0-jongo1.2",
  "ws.securesocial" %% "securesocial" % "master-SNAPSHOT",
  "com.google.apis" % "google-api-services-calendar" % "v3-rev125-1.20.0",
  "com.google.api-client" % "google-api-client" % "1.20.0",
  "com.google.oauth-client" % "google-oauth-client-jetty" % "1.20.0"
//  "com.google.http-client" % "google-http-client-jackson2" % "1.20.0"
)

lazy val root = (project in file(".")).enablePlugins(PlayJava)

resolvers += Resolver.sonatypeRepo("snapshots")

routesGenerator := StaticRoutesGenerator

fork in run := false
