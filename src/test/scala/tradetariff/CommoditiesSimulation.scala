package tradetariff

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import io.gatling.http.protocol.HttpProtocolBuilder

class CommoditiesSimulation extends Simulation   {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(sys.env("PERFTESTURL"))

  val commoditiesFeeder = csv("5000-commodities.csv").queue.circular

  val request =
    feed(commoditiesFeeder)
      .exec(
        http("UK Commodity")
          .get("/commodities/#{commodity}")
      )
      .pause(1)
      .exec(
        http("XI Commodity")
          .get("/xi/commodities/#{commodity}")
      )

  val commoditiesScenario = scenario("Commodities").exec(request)

  setUp(
    commoditiesScenario.inject(
      constantConcurrentUsers(1).during(10.seconds), // 1
      rampConcurrentUsers(1).to(10).during(30.seconds)
    )
  ).protocols(httpProtocol)
}

