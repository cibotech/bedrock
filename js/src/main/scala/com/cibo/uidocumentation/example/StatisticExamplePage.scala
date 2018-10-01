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

package com.cibo.uidocumentation.example

import com.cibo.bedrock.elements.Statistic
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object StatisticExamplePage {

  def render() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Statistic"))),
      <.div(
        ^.cls := "main-view",
        <.div(
          ^.cls := "example-block",
          <.div(
            <.h4("Basic Statistic"),
            Statistic(
              label = "Total",
              value = "1000"
            ),
            CodeExample(
              "scala",
              s"""
                 |    // "label" and "value" are required properties
                 |    Statistic(
                 |        label = "Total",
                 |        value = "1000"
                 |    )
                 |
                 |""".stripMargin
            )
          )
        ),
        <.div(
          ^.cls := "example-block",
          <.div(
            <.h4("Statistic with Units"),
            Statistic(
              label = "temperature",
              value = "98.0",
              units = "°C"
            ),
            CodeExample(
              "scala",
              s"""
                 |    Statistic(
                 |        label = "temperature",
                 |        value = "98.0",
                 |        units = "°C"
                 |    )
                 |
                 |""".stripMargin
            )
          )
        ),
        <.div(
          ^.cls := "example-block",
          <.div(
            <.h4("Multiple Statistics"),
            Statistic(label = "Humidity", value = "74", units = "%"),
            Statistic(label = "temp.", value = "98.0", units = "°C"),
            Statistic(label = "Carbon Dioxide", value = "350", units = "ppm"),
            CodeExample(
              "scala",
              s"""
                 |    // Statistic components are set to "display: inline-block;" by default.
                 |    Statistic(label = "Humidity", value = "98", units = "%"),
                 |    Statistic(label = "temp.", value = "98.0", units = "°C"),
                 |    Statistic(label = "Carbon Dioxide", value = "350", units = "ppm"),
                 |
                 |""".stripMargin
            )
          )
        ),
        <.div(
          ^.cls := "example-block",
          <.div(
            <.h4("Statistics with Statuses"),
            Statistic(label = "success", value = "100.0", status = Statistic.Success, units = "°C"),
            Statistic(label = "default", value = "75.01", status = Statistic.Default, units = "°C"),
            Statistic(label = "warning", value = "48.87", status = Statistic.Warning, units = "°C"),
            Statistic(label = "danger", value = "1.84", status = Statistic.Danger, units = "°C"),
            CodeExample(
              "scala",
              s"""
                 |    Statistic(label = "success", value = "100.0", status=Statistic.Success, units="°C"),
                 |    Statistic(label = "default", value = "75.01", status=Statistic.Default, units="°C"),
                 |    Statistic(label = "warning", value = "48.87", status=Statistic.Warning, units="°C"),
                 |    Statistic(label = "danger", value = "1.84", status=Statistic.Danger, units="°C"),
                 |
                 |""".stripMargin
            )
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("StatisticExample")(render())
}
