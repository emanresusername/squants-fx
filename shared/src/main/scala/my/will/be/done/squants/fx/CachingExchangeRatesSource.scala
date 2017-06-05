package my.will.be.done.squants.fx

import monix.eval.{Task, MVar}
import scala.concurrent.Future
import monix.execution.Scheduler

trait CachingExchangeRatesSource extends ExchangeRatesSource {
  implicit val scheduler: Scheduler
  val cachedExchangeRates = MVar[ExchangeRates](Nil)

  val updateCachedExchangeRates = {
    for {
      _     ← cachedExchangeRates.take
      rates ← Task.deferFuture { super.getExchangeRates }
      _     ← cachedExchangeRates.put(rates)
    } yield {
      rates
    }
  }

  abstract override def getExchangeRates: Future[ExchangeRates] = {
    cachedExchangeRates.read.flatMap {
      case Nil ⇒
        updateCachedExchangeRates
      case exchangeRates ⇒
        Task.now(exchangeRates)
    }.runAsync
  }
}
