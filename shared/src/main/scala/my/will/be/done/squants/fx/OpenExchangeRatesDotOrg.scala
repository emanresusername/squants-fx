package my.will.be.done.squants.fx

import io.circe.generic.auto._
import io.circe.parser.decode
import scala.util.Try

trait OpenExchangeRatesDotOrg extends HttpSource {
  val appId: String
  override val sourceUrl =
    s"https://openexchangerates.org/api/latest.json?app_id=$appId"
  override def parseResponseBody(
      responseBody: String): Try[HttpSource.ResponseBody] = {
    decode[FixerDotIo.ResponseBody](responseBody).toTry
  }
}

object OpenExchangeRatesDotOrg {
  case class ResponseBody(
      base: String,
      rates: Map[String, Double]
  ) extends HttpSource.ResponseBody
}
