package tradetariff

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

class CodeSearchSimulation extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("https://search.staging.trade-tariff.service.gov.uk")
    .header("X-Api-Key", sys.props.get("fpoSearchApiKey").orNull)
    .header("X-Api-Client-Id", "flibble")
    .header("Content-Type", "application/json")

  val codeSearch = csv("fpo-code-search.csv").random

  val request = feed(codeSearch)
    .exec(
      http("CodeSearch_#{query}")
        .get("/fpo-code-search?q=#{query}&digits=8")
    ).pause(1)

  val codeSearchScn = scenario("CodeSearchSimulation").exec(request)

  setUp(
    codeSearchScn.inject(
      rampConcurrentUsers(1).to(4000).during(300.seconds),
      constantConcurrentUsers(4000).during(1500.seconds)
    )
  ).protocols(httpProtocol)
}
