package tradetariff

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder

import scala.concurrent.duration._

class SearchSimulation extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("https://tariff-frontend-staging.london.cloudapps.digital")

  val searchQueries = csv("search_queries.csv").random

  val request = feed(searchQueries)
    .exec(
      http("Search")
        .get("/search?q=${query}")
        .basicAuth(System.getenv("BASIC_AUTH_USERNAME"), System.getenv("BASIC_AUTH_PASSWORD"))
    ).pause(1)

  val searchScenario = scenario("Search").exec(request)

  setUp(
    searchScenario.inject(
      constantConcurrentUsers(1).during(10.seconds), // 1
      rampConcurrentUsers(1).to(20).during(60.seconds)
    )
  ).protocols(httpProtocol)
}
