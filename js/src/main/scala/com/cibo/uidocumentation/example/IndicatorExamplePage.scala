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

import com.cibo.ui.elements.Size.{ExtraLarge, Large, Medium, Small}
import com.cibo.ui.elements.{Icon, Indicator}
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{<, _}
import org.scalajs.dom

object IndicatorExamplePage {

  def render() =
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Indicator"))),
      <.div(
        ^.cls := "main-view",
        <.div(
          ^.cls := "example-block",
          <.div(
            <.h4("Basic Indicators"),
            Indicator(status = Indicator.Success),
            Indicator(status = Indicator.Neutral),
            Indicator(status = Indicator.Warning),
            Indicator(status = Indicator.Danger),
            CodeExample(
              "scala",
              s"""
               |    // Indicators are designed to convey the status of something.
               |    Indicator(status = Indicator.Success),
               |    Indicator(status = Indicator.Neutral), // default
               |    Indicator(status = Indicator.Warning),
               |    Indicator(status = Indicator.Danger)
               |
               |""".stripMargin
            )
          ),
          <.div(
            <.h4("Indicator Size Options"),
            Indicator(size = Small),
            Indicator(size = Medium),
            Indicator(size = Large),
            Indicator(size = ExtraLarge),
            CodeExample(
              "scala",
              s"""
               |    // a smaller size may be more appropriate when combining Indicators with other components
               |    Indicator(size = Indicator.Small),
               |    Indicator(size = Indicator.Medium), // default
               |    Indicator(size = Indicator.Large)
               |    Indicator(size = Indicator.ExtraLarge) // probably too big for most situations
               |
               |""".stripMargin
            )
          ),
          <.div(
            <.h4("Attached Indicators"),
            Icon.notifications(
              Indicator(attached = true, status = Indicator.Warning, size = Small)
            ),
            Icon.message(
              ^.paddingLeft := "1em",
              Indicator(attached = true, status = Indicator.Success, size = Small)
            ),
            <.div(
              ^.position := "relative",
              ^.width := "20em",
              ^.height := "10em",
              ^.border := "1px dotted gray",
              ^.margin := "1em 0",
              ^.padding := "4em",
              ^.textAlign := "center",
              ^.lineHeight := "100%",
              ^.boxSizing := "border-box",
              "This is a relatively positioned element.",
              Indicator(status = Indicator.Danger, attached = true)
            ),
            CodeExample(
              "scala",
              s"""
               |    // "attached" Indicators align to the top-right of "relative" positioned containers.
               |    // Some Bedrock components, like Icon, are relatively positioned by default for this reason.
               |    Icon.notifications(
               |      Indicator(attached = true, status = Indicator.Warning, size = Indicator.Small)
               |    ),
               |    Icon.message(
               |      Indicator(attached = true, status = Indicator.Success, size = Indicator.Small)
               |    ),
               |    <.div(
               |      ^.cls := "some-position-relative-css",
               |      "This is a relatively positioned element.",
               |      Indicator(attached = true, status = Indicator.Danger)
               |    ),
               |
               |""".stripMargin
            )
          ),
          <.div(
            <.h4("Indicators with onClick Handlers"),
            Indicator(
              onClick = Callback { dom.window.alert("Indicator 1") },
              status = Indicator.Success,
            ),
            Indicator(
              onClick = Callback { dom.window.alert("Indicator 2") },
              status = Indicator.Neutral,
            ),
            Indicator(
              onClick = Callback { dom.window.alert("Indicator 3") },
              status = Indicator.Warning,
            ),
            Indicator(
              onClick = Callback { dom.window.alert("Indicator 4") },
              status = Indicator.Danger,
            ),
            CodeExample(
              "scala",
              s"""
               |    // Note that these indicators have hover styles applied.
               |    Indicator(
               |      onClick = Callback { dom.window.alert("Indicator 1") },
               |      status = Indicator.Success,
               |    ),
               |    Indicator(
               |      onClick = Callback { dom.window.alert("Indicator 2") },
               |      status = Indicator.Neutral,
               |    ),
               |    Indicator(
               |      onClick = Callback { dom.window.alert("Indicator 3") },
               |      status = Indicator.Warning,
               |    ),
               |    Indicator(
               |      onClick = Callback { dom.window.alert("Indicator 4") },
               |      status = Indicator.Danger,
               |    ),
               |
                 |""".stripMargin
            )
          ),
        )
      )
    )

  val component = ScalaComponent.static("IndicatorExample")(render())
}
