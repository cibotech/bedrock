/*
 * Copyright (c) 2018, CiBO Technologies, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.cibo.ui.elements

import java.time.LocalDate

import com.cibo.ui.Util
import japgolly.scalajs.react.{BackendScope, _}
import japgolly.scalajs.react.vdom.html_<^._

object Calendar {

  case class Props()

  case class State(today: LocalDate, currentMonth: Int, currentYear: Int)

  def dayValueToChar(dayI: Int): String = { if (dayI > 7) dayI - 7 else dayI } match {
    case 1 => "M"
    case 2 => "T"
    case 3 => "W"
    case 4 => "T"
    case 5 => "F"
    case 6 => "S"
    case 7 => "S"
  }

  // scalastyle:off
  def monthValueToMonth(monthI: Int): String = { monthI } match {
    case 1  => "January"
    case 2  => "February"
    case 3  => "March"
    case 4  => "April"
    case 5  => "May"
    case 6  => "June"
    case 7  => "July"
    case 8  => "August"
    case 9  => "September"
    case 10 => "October"
    case 11 => "November"
    case 12 => "December"
  }
  // scalastyle:on

  class Backend($ : BackendScope[Props, State]) {
    def plusMonth() = {
      $.modState { x =>
        val (newMonth, newYear) =
          if (x.currentMonth == 12) (1, x.currentYear + 1)
          else (x.currentMonth + 1, x.currentYear)
        x.copy(currentMonth = newMonth, currentYear = newYear)
      }
    }
    def minusMonth() = {
      $.modState { x =>
        val (newMonth, newYear) =
          if (x.currentMonth == 1) (12, x.currentYear - 1)
          else (x.currentMonth - 1, x.currentYear)
        x.copy(currentMonth = newMonth, currentYear = newYear)
      }
    }

    def render(p: Props, s: State) = {
      val lastMonth = {
        if (s.currentMonth - 1 < 1) LocalDate.of(s.currentYear - 1, 12, 1)
        else LocalDate.of(s.currentYear, s.currentMonth - 1, 1)
      }

      val nextMonth = {
        if ((s.currentMonth + 1) > 12) LocalDate.of(s.currentYear + 1, 1, 1)
        else LocalDate.of(s.currentYear, s.currentMonth + 1, 1)
      }

      val firstDayOfMonth = LocalDate.of(s.currentYear, s.currentMonth, 1)
      val lastDayOfMonth = LocalDate.of(s.currentYear,
                                        s.currentMonth,
                                        firstDayOfMonth.getMonth.length(firstDayOfMonth.isLeapYear))

      val previousDays = 7 - (7 - firstDayOfMonth.getDayOfWeek.getValue + 1)
      val nextDays = 7 - {
        if ((lastDayOfMonth.getDayOfWeek.getValue + 1) > 7) 1
        else (lastDayOfMonth.getDayOfWeek.getValue + 1)
      }

      val lastMonthDays = lastMonth.getMonth.length(lastMonth.isLeapYear) - (previousDays)
      val nextMonthDays = nextDays

      val days = firstDayOfMonth.getMonth.length(firstDayOfMonth.isLeapYear)

      val lastDaysEle = (lastMonthDays to lastMonth.getMonth.length(lastMonth.isLeapYear)).map {
        last =>
          val date = lastMonth.withDayOfMonth(last)

          <.td(^.cls := "day non-month",
               <.div(^.cls := "aspect-ratio-container",
                     <.div(^.cls := "content", <.div(^.cls := "day-number", last))))
      } match {
        case isFullWeek if isFullWeek.length == 7 => Seq()
        case isPartialWeek                        => isPartialWeek
      }

      val currentDaysEle = (1 to days).map { current =>
        val date = firstDayOfMonth.withDayOfMonth(current).toEpochDay
        <.td(
          ^.cls := "day",
          <.div(
            ^.cls := "aspect-ratio-container",
            <.div(
              ^.cls := "content",
              <.div(^.cls := "day-number", current),
              ("Today").when(
                current == s.today.getDayOfMonth &&
                  s.today.getMonthValue == s.currentMonth &&
                  s.today.getYear == s.currentYear)
            )
          )
        )
      }
      val nextDaysEle = (1 to nextMonthDays).map { next =>
        val date = nextMonth.withDayOfMonth(next)

        <.td(^.cls := "day non-month",
             <.div(^.cls := "aspect-ratio-container",
                   <.div(^.cls := "content", <.div(^.cls := "day-number", next))))
      }

      <.div(
        ^.cls := "basic-calendar",
        <.div(
          ^.cls := "row",
          <.div(^.cls := "previous-button", Icon.chevronLeft, ^.onClick --> minusMonth),
          <.span(^.cls := "month-label",
                 s"${monthValueToMonth(firstDayOfMonth.getMonth.getValue)}"),
          <.div(^.cls := "next-button", Icon.chevronRight, ^.onClick --> plusMonth)
        ),
        <.table(
          <.thead(
            <.tr(
              <.th(dayValueToChar(7)),
              <.th(dayValueToChar(1)),
              <.th(dayValueToChar(2)),
              <.th(dayValueToChar(3)),
              <.th(dayValueToChar(4)),
              <.th(dayValueToChar(5)),
              <.th(dayValueToChar(6))
            )
          ),
          <.tbody(
            { lastDaysEle ++ currentDaysEle ++ nextDaysEle }.grouped(7).toTagMod { week =>
              <.tr(^.cls := "week", week.toTagMod)
            }
          )
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props]("Calendar")
    .initialState {
      val today = LocalDate.now
      State(today, currentMonth = today.getMonthValue, currentYear = today.getYear)
    }
    .renderBackend[Backend]
    .build

  def apply() = component(Props())
}
