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

import com.cibo.ui.ReactGridStrict._
import com.cibo.ui.elements.{Button, Tabs}
import com.cibo.ui.elements.Tabs.Tab
import com.cibo.ui.notifications.{GlobalAlertSystem, Severity, Snack}
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object NotificationExamplePage {

  def render() = {

    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Navigation"))),
      <.div(
        ^.cls := "main-view",
        <.div(
          ^.cls := "example-block",
          row(
            column(12)(
              <.h4("Global Alert System"),
              CodeExample(
                "scala",
                s"""
                   |GlobalAlertSystem() // put this component in the root of your app
                """.stripMargin
              )
            ),
            column(12)(
              <.h4("Snackbar"),
              row(
                column(12)(
                  Button("Info alert left").apply(^.onClick -->
                    GlobalAlertSystem.pushToBtmLeft(Snack(s"Info alert"))),
                  Button("Error alert left").error.apply(^.onClick -->
                    GlobalAlertSystem.pushToBtmLeft(Snack("Error alert", severity = Severity.Error))),
                  Button("Success alert left").success.apply(^.onClick -->
                    GlobalAlertSystem.pushToBtmLeft(Snack("Success alert", severity = Severity.Success)))
                )
              ),
              row(
                column(12)(
                  Button("Info alert right").apply(^.onClick -->
                    GlobalAlertSystem.pushToBtmRight(Snack(s"Info alert"))),
                  Button("Error alert right").error.apply(^.onClick -->
                    GlobalAlertSystem.pushToBtmRight(Snack("Error alert", severity = Severity.Error))),
                  Button("Success alert right").success.apply(^.onClick -->
                    GlobalAlertSystem.pushToBtmRight(Snack("Success alert", severity = Severity.Success)))
                )
              ),
              row(
                column(12)(
                  Button("Alert with lots of text, avoid this").apply(^.onClick -->
                    GlobalAlertSystem.pushToBtmLeft(Snack(s"Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                      s"sed do eiusmod tempor incididunt ut " +
                      s"labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
                      s"ullamco laboris nisi ut aliquip ex ea commodo consequat." +
                      s" Duis aute irure dolor in reprehenderit in voluptate velit " +
                      s"esse cillum dolore eu fugiat nulla pariatur."))),
                )
              ),
              CodeExample(
                "scala",
                s"""
                   | column(12)(
                   |   Button("Info alert left").apply(^.onClick -->
                   |     GlobalAlertSystem.pushToBtmLeft(Snack(s"Info alert"))),
                   |   Button("Error alert left").error.apply(^.onClick -->
                   |     GlobalAlertSystem.pushToBtmLeft(Snack("Error alert", severity = Severity.Error))),
                   |   Button("Success alert left").success.apply(^.onClick -->
                   |     GlobalAlertSystem.pushToBtmLeft(Snack("Success alert", severity = Severity.Success)))
                   | )
                      """.stripMargin
              )
            )
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("NavigationExample")(render())
}
