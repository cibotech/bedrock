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

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Statistic {

  sealed trait StatisticStatus { val className: String }
  case object Success extends StatisticStatus { val className = "success" }
  case object Default extends StatisticStatus { val className = "" }
  case object Warning extends StatisticStatus { val className = "warning" }
  case object Danger extends StatisticStatus { val className = "danger" }

  final case class Props(
      label: Text,
      value: String,
      units: Option[String],
      status: StatisticStatus
  )

  private val component = ScalaComponent
    .builder[Props]("Statistic")
    .render_P(props =>
      <.div(
        ^.cls := "statistic",
        <.div(
          ^.cls := "label",
          props.label
        ),
        <.div(
          ^.cls := s"value ${props.status.className}",
          props.value,
          if (props.units.isDefined)
            <.span(
              ^.cls := "units",
              props.units.get
            )
          else EmptyVdom
        )
    ))
    .build

  def apply(
      label: String,
      value: String,
      units: String = "",
      status: StatisticStatus = Default
  ) =
    component(
      Props(
        label = label,
        value = value,
        units = if (units != "") Some(units) else None,
        status = status
      ))
}
