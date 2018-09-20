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
import com.cibo.ui.elements._
import com.cibo.ui.pane.{LoadablePane, Pane, PaneHeader}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import com.cibo.ui.elements.Text._
import com.cibo.uidocumentation.CodeExample
import com.cibo.ui._
import com.cibo.uidocumentation.example.TableExamplePage.{TableKeys, rows}

object PaneExamplePage {

  def render() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Panes"))),
      <.div(
        ^.cls := "main-view",
        <.div(
          ^.cls := "example-block",
          row(
            column(12)(
              Pane()(
                <.div(^.cls := "text-center", <.h4(^.cls := "title", "Card Pane 1"))
              )
            ),
            column(6)(
              Pane()(
                <.div(^.cls := "text-center", <.h4(^.cls := "title", "Card Pane 2"))
              )
            ),
            column(6)(
              Pane()(
                <.div(^.cls := "text-center", <.h4(^.cls := "title", "Card Pane 3"))
              )
            ),
            column(12)(CodeExample( "scala",
              """
                | column(12)(
                |   Pane()(
                |     <.div(^.cls := "text-center", <.h4(^.cls := "title", "Card Pane 1"))
                |   )
                | ),
                | column(6)(
                |   Pane()(
                |     <.div(^.cls := "text-center", <.h4(^.cls := "title", "Card Pane 2"))
                |   )
                | ),
                | column(6)(
                |   Pane()(
                |     <.div(^.cls := "text-center", <.h4(^.cls := "title", "Card Pane 3"))
                |   )
                | ),
              """.stripMargin
            )),
            column(6)(
              Pane(
                Some(PaneHeader(title = Some(Text("Pane With Header"))))
              )(
                <.div(^.cls := "text-center", <.h4(^.cls := "title", "Content"))
              )
            ),
            column(6)(
              Pane(
                Some(PaneHeader(
                  title = Some("Pane With Controls"),
                  Seq(
                    Button(Icon.add).secondary.small.rounded.onClick(Callback(println("Add Hit"))),
                    Button(Icon.close).secondary.small.rounded.onClick(Callback(println("Close Hit")))
                  )
                ))
              )(
                <.div(^.cls := "text-center", <.h4(^.cls := "title", "Content"))
              )
            ),
            column(12)(CodeExample( "scala",
              """
                | column(6)(
                |   Pane(
                |     Some(PaneHeader(title = Some(Text("Pane With Header"))))
                |   )(
                |     <.div(^.cls := "text-center", <.h4(^.cls := "title", "Content"))
                |   )
                | ),
                | column(6)(
                |   Pane(
                |     Some(PaneHeader(
                |       title = Some("Pane With Controls"),
                |       Seq(
                |         Control(Callback(println("Control Add Hit")), Icon.add),
                |         Control(Callback(println("Control Close Hit")), Icon.close)
                |       )
                |     ))
                |   )(
                |     <.div(^.cls := "text-center", <.h4(^.cls := "title", "Content"))
                |   )
                | ),
              """.stripMargin
            )),
            column(12)(
              Pane(lowProfile = true)(
                <.div(<.h4(^.cls := "title", "Normal Pane")).centerText
              )
            ),
            column(12)(CodeExample( "scala",
              """
                |column(12)(
                |  Pane(lowProfile = true)(
                |    <.div(^.cls := "text-center", <.h4(^.cls := "title", "Normal Pane"))
                |  )
                |),
              """.stripMargin
            )),
            LoadablePane(6)(Some("Finished")),
            LoadablePane(6)(None),
            column(12)(
              Pane(
                Some(PaneHeader(
                  title = Some("Pane With scroll")
                ))
              )(
                ^.maxHeight := "400px",
                row(
                  column(12)(
                    GridExamplePage.component()
                  )
                )
              )
            )
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("InputExample")(render())
}
