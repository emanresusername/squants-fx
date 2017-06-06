package my.will.be.done.squants.fx

import io.circe.generic.auto._
import io.circe.parser.decode
import scala.util.Try

trait OpenExchangeRatesDotOrg extends UrlSource {
  val appId: String
  override def sourceUrl: String =
    s"https://openexchangerates.org/api/latest.json?app_id=$appId"
  override def parseResponseBody(
      responseBody: String): Try[HttpSource.ResponseBody] = {
    decode[OpenExchangeRatesDotOrg.ResponseBody](responseBody).toTry
  }
}

object OpenExchangeRatesDotOrg {
  case class ResponseBody(
      base: String,
      rates: Map[String, Double]
  ) extends HttpSource.ResponseBody
}
