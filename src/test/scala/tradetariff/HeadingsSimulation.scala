package tradetariff

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import io.gatling.http.protocol.HttpProtocolBuilder

class HeadingsSimulation extends Simulation   {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(sys.env("PERFTESTURL"))

  val headingsFeeder = csv("all-headings.csv").queue.circular

  val request =
    feed(headingsFeeder)
      .exec(
        http("UK Commodity")
          .head("/headings/#{heading}")
      )
      .pause(1)
      .exec(
        http("XI Commodity")
          .head("/xi/headings/#{heading}")
      )

  val headingsScenario = scenario("Headings").exec(request)

  setUp(
    headingsScenario.inject(
      constantConcurrentUsers(1).during(10.seconds), // 1
      rampConcurrentUsers(1).to(10).during(60.seconds),
      constantConcurrentUsers(10).during(600.seconds)
    )
  ).protocols(httpProtocol)
}
