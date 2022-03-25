package tradetariff

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder


class CommoditiesSimulation extends Simulation   {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("https://tariff-frontend-staging.london.cloudapps.digital")

  val commoditiesFeeder = csv("commodities.csv").random

  val request =
    feed(commoditiesFeeder)
      .exec(
        http("UK Commodity")
          .get("/commodities/${commodity}")
      )
      .pause(1)
      .exec(
        http("XI Commodity")
          .get("/xi/commodities/${commodity}")
      )

  val commoditiesScenario = scenario("Commodities").exec(request)

  setUp(
    commoditiesScenario.inject(
      constantConcurrentUsers(1).during(10.seconds), // 1
      rampConcurrentUsers(1).to(10).during(30.seconds)
    )
  ).protocols(httpProtocol)
}

