package com.cibo.bedrock.elements

import com.cibo.bedrock.input.ToggleInput
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.test.{ReactTestUtils, _}
import org.scalatest.{FunSpec, Matchers}

class ToggleSpec extends FunSpec with Matchers {

  describe("Toggle element works") {

    it("Inits properly") {

      ReactTestUtils.withRenderedIntoDocument(ToggleInput(true, onChange = { v: Boolean =>
        Callback {}
      })) { comp =>
        assert(comp.outerHtmlScrubbed().contains("initial-true"))
      }
    }

    it("Will toggle when circle clicked") {
      var Toggled: Boolean = false // scalastyle:ignore

      ReactTestUtils.withRenderedIntoDocument(ToggleInput(Toggled, onChange = { v: Boolean =>
        Callback {
          Toggled = v
        }
      })) { comp =>
        Simulation.click.run(comp.getDOMNode.getElementsByClassName("toggle-indicator").item(0))

        assert(Toggled)
        assert(comp.outerHtmlScrubbed().contains("true"))
      }
    }

    it("Will toggle when pill clicked") {
      var Toggled: Boolean = false // scalastyle:ignore

      ReactTestUtils.withRenderedIntoDocument(ToggleInput(Toggled, onChange = { v: Boolean =>
        Callback {
          Toggled = v
        }
      })) { comp =>
        Simulation.click.run(comp.getDOMNode.getElementsByClassName("interior-wrapper").item(0))

        assert(Toggled)
        assert(comp.outerHtmlScrubbed().contains("true"))
      }
    }

  }

}
