enablePlugins(GatlingPlugin)

scalaVersion := "3.2.2"

scalacOptions := Seq(
  "-encoding", "UTF-8", "-release", "8", "-deprecation",
  "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

val gatlingVersion = "3.9.5"
libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion % "test"
libraryDependencies += "io.gatling"            % "gatling-test-framework"    % gatlingVersion % "test"
