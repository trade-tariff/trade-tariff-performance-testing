package tradetariff

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class BrowseSimulation extends Simulation {

  private val httpProtocol = http
    .baseUrl("https://staging.trade-tariff.service.gov.uk")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")

  val feeder = csv("browse-commodities.csv").random

  val request = feed(feeder)
      .exec(
        http("Load Section ${section}")
          .get("/sections/${section}")
      )
      .pause(1)
      .exec(
        http("Load Chapter ${chapter}")
          .get("/chapters/${chapter}")
      )
      .pause(1)
      .exec(
        http("Load Heading ${heading}")
          .get("/headings/${heading}")
      )
      .pause(1)
      .exec(
        http("Load Commodity ${commodity}")
          .get("/commodities/${commodity}")
      )

    val scn = scenario("BrowseSimulation").exec(request)

    setUp(
      scn.inject(
        constantConcurrentUsers(1).during(10.seconds), // 1
        rampConcurrentUsers(1).to(20).during(60.seconds) // 2
      )
    ).protocols(httpProtocol)
}
