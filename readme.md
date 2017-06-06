[![](https://jitpack.io/v/emanresusername/squants-fx.svg)](https://jitpack.io/#emanresusername/squants-fx)

# Usage
```scala
import my.will.be.done.squants.fx._
case object FixerMoneyContextSource 
  extends MoneyContextSource 
  with FixerDotIo 
  with CachingExchangeRatesSource {
  implicit val scheduler = monix.execution.Scheduler.Implicits.global
  implicit val executionContext = scheduler
}

import FixerMoneyContextSource.moneyContext
squants.money.USD(1) in JPY
```

# Data Sources
## [fixer.io](http://fixer.io/)
## [openexchangerates.org](https://openexchangerates.org/)
requires [signup](https://openexchangerates.org/signup) and [app id](https://openexchangerates.org/account)
