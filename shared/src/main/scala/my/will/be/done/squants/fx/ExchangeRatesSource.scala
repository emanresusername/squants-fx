package my.will.be.done.squants.fx

import squants.market.CurrencyExchangeRate
import scala.concurrent.Future

trait ExchangeRatesSource {
  type ExchangeRates = List[CurrencyExchangeRate]
  def getExchangeRates: Future[ExchangeRates]
}
