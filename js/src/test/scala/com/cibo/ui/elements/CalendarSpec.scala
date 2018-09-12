package com.cibo.ui.elements

import java.time.LocalDate

import japgolly.scalajs.react.test._
import org.scalatest.{FunSpec, Matchers}

class xCalendarSpec extends FunSpec with Matchers {

  describe("Calender") {

    it("Creates a calendar with current date") {
      val currentDate = LocalDate.now

      ReactTestUtils.withRenderedIntoDocument(Calendar()) { comp =>
        assert(comp.state.currentMonth == currentDate.getMonthValue)
        assert(comp.state.currentYear == currentDate.getYear)
        assert(comp.state.today == currentDate)
      }
    }

    it("Changes to the next month when button is pressed") {
      ReactTestUtils.withRenderedIntoDocument(Calendar()) { comp =>
        Simulation.click.run(comp.getDOMNode.getElementsByClassName("next-button")(0))
        val nextMonth =
          if (LocalDate.now.getMonthValue == 12) 1
          else LocalDate.now.getMonthValue + 1

        assert(comp.outerHtmlScrubbed().contains(Calendar.monthValueToMonth(nextMonth)))
      }
    }

    it("Changes to the perevious month when button is pressed") {
      ReactTestUtils.withRenderedIntoDocument(Calendar()) { comp =>
        Simulation.click.run(comp.getDOMNode.getElementsByClassName("previous-button").item(0))
        val previousMonth =
          if (LocalDate.now.getMonthValue == 1) 12
          else LocalDate.now.getMonthValue - 1

        assert(comp.outerHtmlScrubbed().contains(Calendar.monthValueToMonth(previousMonth)))
      }
    }

  }

}
