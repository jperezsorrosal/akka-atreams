name := "akka-streams"

version := "1.0"

scalaVersion := "2.11.8"

val akkaVersion = "2.4.11"


libraryDependencies += "com.typesafe.akka" %% "akka-stream" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion