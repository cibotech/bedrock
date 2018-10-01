import com.cibo.bedrock.util.EventHelper.ThrottledFunction
import org.scalatest._

import scala.concurrent.Promise
import scala.scalajs.js.timers
import scala.util.Try

class EventHelpersSpec extends AsyncFunSpec with Matchers {
  implicit override def executionContext =
    scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  describe("Debounce") {

    var callCount: Int = 0 // scalastyle:ignore
    it("Throttles the call correctly") {
      val delayMs: Int = 15
      val promise = Promise[Assertion]()

      def doIt(x: Int): Unit = {
        callCount = callCount + 1
      }

      val debouncer = ThrottledFunction(delayMs)
      def debouncedDoIt(x: Int) = debouncer { () =>
        doIt(x)
      }

      debouncedDoIt(1)
      debouncedDoIt(2)
      debouncedDoIt(3)

      timers.setTimeout(delayMs + 10) {
        debouncedDoIt(4)
      }

      timers.setTimeout(delayMs + 60) {
        promise.complete(Try({ callCount shouldEqual (2) }))
      }
      promise.future
    }
  }
}
