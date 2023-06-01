package tradetariff

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

class SectionsSimulation extends Simulation {
  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("https://tariff-frontend-staging.london.cloudapps.digital")

  val sectionFeeder = jsonFile("sections.json").random

  val request = exec(
    http("Sections index")
      .get("/sections")
  ).pause(1)
    .feed(sectionFeeder)
    .exec(
      http("Section #{section}")
        .get("/sections/#{section}")
    )
    .pause(1)
    .exec(
      http("Random Chapter")
        .get("/chapters/#{chapters.random()}")
    )

  val sectionsScenario = scenario("Sections").exec(request)
  setUp(
    sectionsScenario.inject(
      constantConcurrentUsers(1).during(10.seconds), // 1
      rampConcurrentUsers(1).to(25).during(60.seconds)
    )
  ).protocols(httpProtocol)
}

