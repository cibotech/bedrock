package com.cibo.bedrock.elements

import com.cibo.bedrock.input.SliderInput
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.test.{ReactTestUtils, SimEvent}
import org.scalatest.{AsyncFunSpec, FunSpec}

import scala.concurrent.Future


class SliderSyncSpec extends FunSpec {
  it("allows us to create a slider far away from zero") {
    var value = 51 // scalastyle:ignore
    SliderInput.Int(
      min = 50,
      max = 100,
      step = 1,
      initialValue = value,
      onChange = { v =>
        Callback { value = v }
      })
  }

  it("blows up if starting position is below min") {
    var value = 0 // scalastyle:ignore
    assertThrows[AssertionError](SliderInput.Int(
      min = 1,
      max = 11,
      step = 1,
      initialValue = value,
      onChange = { v =>
        Callback { value = v }
      }))
  }

  it("blows up if starting position is above max") {
    var value = 12 // scalastyle:ignore
    assertThrows[AssertionError](SliderInput.Int(
      min = 1,
      max = 11,
      step = 1,
      initialValue = value,
      onChange = { v =>
        Callback { value = v }
      }))
  }

  it("blows up if step is wider than the range") {
    var value = 1 // scalastyle:ignore
    assertThrows[AssertionError](SliderInput.Int(
      min = 1,
      max = 11,
      step = 50,
      initialValue = value,
      onChange = { v =>
        Callback { value = v }
      }))
  }

  it("blows up if min is greater than max") {
    var value = 1 // scalastyle:ignore
    assertThrows[AssertionError](SliderInput.Int(
      min = 11,
      max =1,
      step = 2,
      initialValue = value, onChange = { v =>
        Callback { value = v }
      }))
  }

}
class SliderAsyncSpec extends AsyncFunSpec {

  describe("Slider") {
    it("appropriately tracks state changes") {
      var value = 1 // scalastyle:ignore
      val slider = SliderInput.Int(
        min = 0,
        max = 100,
        step = 1,
        initialValue = value,
        onChange = { v =>
          Callback { value = v }
        })

      ReactTestUtils.withRenderedIntoDocument(slider) { comp =>
        val domSlider = comp.getDOMNode.getElementsByTagName("input").item(0)
        SimEvent.Change(value = "10").simulation.run(domSlider)
        Future { assert(value == 10) }
      }
    }


    it("works with doubles as well") {
      var onChangeValue = 5.0 // scalastyle:ignore
      val slider = SliderInput.Double(
        min = 0,
        max = 100,
        step = 0.5,
        initialValue = onChangeValue,
        onChange = { v =>
          Callback { onChangeValue = v }
        })

      ReactTestUtils.withRenderedIntoDocument(slider) { comp =>
        val domSlider = comp.getDOMNode.getElementsByTagName("input").item(0)
        SimEvent.Change(value = "10.5").simulation.run(domSlider)
        Future { assert(onChangeValue == 10.5) }
      }
    }
  }
}
