package my.will.be.done.squants.fx

import io.circe.generic.auto._
import io.circe.parser.decode
import scala.util.Try

trait FixerDotIo extends HttpSource {
  override val sourceUrl = "https://api.fixer.io/latest"
  override def parseResponseBody(
      responseBody: String): Try[HttpSource.ResponseBody] = {
    decode[FixerDotIo.ResponseBody](responseBody).toTry
  }
}

object FixerDotIo {
  case class ResponseBody(
      base: String,
      rates: Map[String, Double]
  ) extends HttpSource.ResponseBody
}
