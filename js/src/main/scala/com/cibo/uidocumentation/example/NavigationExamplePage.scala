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
import com.cibo.ui.elements.Tabs
import com.cibo.ui.elements.Tabs.Tab
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object NavigationExamplePage {

  def render() = {

    val tabs = Seq(
      Tab("one", column(12) {
        <.div(^.cls := "tabs-example", <.h4("Tab One"))
      }),
      Tab("two", column(12) {
        <.div(^.cls := "tabs-example", <.h4("Tab Two"))
      })
    )

    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Navigation"))),
      <.div(
        ^.cls := "main-view",
        <.div(
          ^.cls := "example-block",
          row(
            column(12)(
              <.h4("Global Navigation Definition"),
              CodeExample(
                "scala",
                s"""
                   |object DocumentationNav extends Navigation[DocumentationPage] {
                   |  def router : RouterCtl[DocumentationPage] = DocumentationRouter.ctl
                   |}
                      """.stripMargin
              )
            ),
            column(12)(
              <.h4("Global Navigation Usage"),
              CodeExample(
                "scala",
                s"""
                   |  DocumentationNav.init()
                   |
                   |  val navigation: Seq[NavigationPage[DocumentationPage]] = Seq(
                   |    NavigationPage("Home", HomePage),
                   |    NavigationPage("Grid System", GridDocs),
                   |    NavigationPage("Calendar", CalendarDocs),
                   |    NavigationPage("Input Elements", InputDocs),
                   |    NavigationPage("Table", TableDocs),
                   |  )
                   |
                   |
                   |  def layout(c: RouterCtl[DocumentationPage], r: Resolution[DocumentationPage]) = {
                   |    ctl = c
                   |    <.div(^.cls := "dashboard-viewport", DocumentationNav(r.page, navigation, "UI"), r.render())
                   |  }
                      """.stripMargin
              )
            ),
            column(12)(
              <.h4("Tabs Example"),
              Tabs(initialTab = tabs.head, tabs)
            ),
            column(12)(
              <.h4("Tabs Example Code"),
              CodeExample(
                "scala",
                s"""
                   |
                   |    // definition
                   |    val tabs = Seq(
                   |      Tab("one", column(12){
                   |        <.div( ^.cls := "tabs-example", <.h4("Tab One"))
                   |      }),
                   |      Tab("two", column(12){
                   |        <.div( ^.cls := "tabs-example", <.h4("Tab Two"))
                   |      })
                   |    )
                   |
                   |    // component creation
                   |    Tabs( initialTab = tabs.head, tabs)
                   |
                   |
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
