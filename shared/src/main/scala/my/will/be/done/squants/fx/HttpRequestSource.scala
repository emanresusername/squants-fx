package my.will.be.done.squants.fx

import monix.execution.Scheduler
import monix.eval.Task
import fr.hmil.roshttp.HttpRequest
import squants.market.defaultCurrencyMap
import scala.util.Try
import scala.concurrent.Future

trait HttpRequestSource extends ExchangeRatesSource {
  implicit val scheduler: Scheduler
  def httpRequest: HttpRequest

  def parseResponseBody(responseBody: String): Try[HttpSource.ResponseBody]

  override def getExchangeRates: Future[ExchangeRates] = {
    (for {
      response     ← Task.deferFuture { httpRequest.send }
      responseBody ← Task.fromTry(parseResponseBody(response.body))
      base         ← Task.eval { defaultCurrencyMap(responseBody.base) }
    } yield {
      responseBody.rates.flatMap {
        case (key, value) ⇒
          defaultCurrencyMap.get(key).filterNot(base.equals).map { currency ⇒
            base / currency(value)
          }
      }.toList
    }).runAsync
  }
}

object HttpSource {
  trait ResponseBody {
    def base: String
    def rates: Map[String, Double]
  }
}
