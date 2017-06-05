package my.will.be.done.squants.exchangerates

import monix.execution.Scheduler
import monix.eval.Task
import fr.hmil.roshttp.HttpRequest
import squants.market.defaultCurrencyMap
import scala.util.Try
import scala.concurrent.Future

trait HttpSource extends ExchangeRatesSource {
  implicit val scheduler: Scheduler
  val sourceUrl: String

  def parseResponseBody(responseBody: String): Try[HttpSource.ResponseBody]

  override def getExchangeRates: Future[ExchangeRates] = {
    (for {
      response     ← Task.deferFuture { HttpRequest(sourceUrl).get }
      responseBody ← Task.fromTry(parseResponseBody(response.body))
      base         ← Task.eval { defaultCurrencyMap(responseBody.base) }
    } yield {
      responseBody.rates.flatMap {
        case (key, value) ⇒
          defaultCurrencyMap.get(key).map { currency ⇒
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
