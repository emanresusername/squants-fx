package my.will.be.done.squants.exchangerates

import squants.market.{defaultMoneyContext, MoneyContext}
import scala.concurrent.{ExecutionContext, Await}
import scala.concurrent.duration._

trait MoneyContextSource extends ExchangeRatesSource {
  implicit val executionContext: ExecutionContext
  val getExchangeRatesTimeout = 10.seconds
  implicit def moneyContext: MoneyContext = {
    Await.result(getExchangeRates.map { exchangeRates ⇒
      defaultMoneyContext withExchangeRates exchangeRates
    }, getExchangeRatesTimeout)
  }
}
