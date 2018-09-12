package com.cibo.util

import scala.scalajs.js.timers
import scala.scalajs.js.timers.SetTimeoutHandle

object EventHelper {
  trait Throttled {
    protected val waitTimeMs: Int
    private var timeout: Option[SetTimeoutHandle] = None //scalastyle:ignore
    def waitForExecute(function: () => _) = {
      timeout.map(t => timers.clearTimeout(t))
      timeout = Some(timers.setTimeout(waitTimeMs) { function() })
    }
  }

  object ThrottledFunction {
    def apply(waitMs: Int) = {
      val t = new Throttled {
        val waitTimeMs = waitMs
      }
      (a: () => _) =>
        t.waitForExecute(a)
    }
  }
}
