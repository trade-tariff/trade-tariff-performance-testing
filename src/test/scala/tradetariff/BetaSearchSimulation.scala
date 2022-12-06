package tradetariff

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder

import scala.concurrent.duration._

class BetaSearchSimulation extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("https://staging.trade-tariff.service.gov.uk")

  val searchQueries = csv("search_queries.csv").random

  val request = feed(searchQueries)
    .exec(
      http("BetaSearch ${query}")
        .get("/uk/api/beta/search?q=${query}")
        .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.1 Safari/605.1.15")
        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
        .header("Accept-Encoding", "gzip, deflate, br")
        .header("Connection", "keep-alive")
        // .basicAuth(System.getenv("BASIC_AUTH_USERNAME"), System.getenv("BASIC_AUTH_PASSWORD"))
    ).pause(1)

  val searchScenario = scenario("BetaSearch").exec(request)

  setUp(
    searchScenario.inject(
      rampConcurrentUsers(1).to(25).during(60.seconds),
      constantConcurrentUsers(25).during(5.minutes)
    ).protocols(httpProtocol)
  )
}
