name := "scala-playground"

organization := "com.ophchu"

version := "0.0.1"

scalaVersion := "2.12.10"

sbtVersion := "0.13"


val akkaVersion = "2.6.1"

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.1.0" % "test",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "com.typesafe" % "config" % "1.3.0",
//  "com.typesafe.akka" %% "akka-actor" % "2.6.1",




  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test
)
