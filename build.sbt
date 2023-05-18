import sbt._
import Keys._

name := "iptoolproject"
version := "1.0"
scalaVersion := "2.13.8"

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

libraryDependencies ++= Seq(
  "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % "3.3.10",
  "org.scalameta" % "sbt-scalafmt" % "2.4.2"
)

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.2")
