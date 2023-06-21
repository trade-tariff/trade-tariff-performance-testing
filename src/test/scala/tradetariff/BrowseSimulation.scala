package tradetariff

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BrowseSimulation extends Simulation {

  private val httpProtocol = http
    .baseUrl(sys.env("PERFTESTURL"))
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")

  val feeder = csv("random-browse-commodities.csv").queue.circular

  val request = feed(feeder)
      .exec(
        http("Load Section #{section}")
          .head("/sections/#{section}")
      )
      .pause(1)
      .exec(
        http("Load Chapter #{chapter}")
          .head("/chapters/#{chapter}")
      )
      .pause(1)
      .exec(
        http("Load Heading #{heading}")
          .head("/headings/#{heading}")
      )
      .pause(1)
      .exec(
        http("Load Commodity #{commodity}")
          .head("/commodities/#{commodity}")
      )

    val scn = scenario("BrowseSimulation").exec(request)

    setUp(
      scn.inject(
        constantConcurrentUsers(1).during(30.seconds), // 1
        rampConcurrentUsers(1).to(50).during(120.seconds) // 2
      )
    ).protocols(httpProtocol)
}
