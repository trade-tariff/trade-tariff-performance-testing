package tradetariff

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder

import scala.concurrent.duration._

class ApiQuotasSimulation extends Simulation {
  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("https://staging.trade-tariff.service.gov.uk/api/v2")

  val request = exec(
    http("Get quota order numbers")
      .get("/quota_order_numbers")
      .basicAuth(System.getenv("BASIC_AUTH_USERNAME"), System.getenv("BASIC_AUTH_PASSWORD"))
  ).pause(1)

  val sectionsScenario = scenario("API V2 Quotas").exec(request)
  setUp(
    sectionsScenario.inject(
      constantConcurrentUsers(1).during(10.seconds), // 1
      rampConcurrentUsers(1).to(100).during(30.seconds)
    )
  ).protocols(httpProtocol)
}

