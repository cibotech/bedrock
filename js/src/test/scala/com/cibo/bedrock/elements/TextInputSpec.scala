package com.cibo.bedrock.elements

import com.cibo.bedrock.input.TextInput
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.test.{ReactTestUtils, _}
import org.scalatest.{FunSpec, Matchers}

class TextInputSpec extends FunSpec with Matchers {

  describe("Text Input element works") {

    it("Inits properly") {

      ReactTestUtils.withRenderedIntoDocument(TextInput("InitialText", onChange = { _ =>
        Callback {}
      })) { comp =>
        assert(comp.outerHtmlScrubbed().contains("InitialText"))
      }
    }

    it("Text changes") {
      var inputValue: String = "" // scalastyle:ignore

      ReactTestUtils.withRenderedIntoDocument(TextInput("", onChange = { v: String =>
        Callback {
          inputValue = v
        }
      })) { comp =>
        Simulation.change(SimEvent.Change.apply("TestInputString")).run(comp)

        assert(inputValue != "")
        assert(comp.outerHtmlScrubbed().contains("TestInputString"))
      }
    }

    it("works with placeholder text") {
      val placeholderText = "blah"
      ReactTestUtils.withRenderedIntoDocument(TextInput.withPlaceHolder(placeholderText, onChange = { _ =>
        Callback {}
      })) { comp =>
        assert(comp.getDOMNode.getAttribute("placeholder") == placeholderText)
      }

    }
  }

}
