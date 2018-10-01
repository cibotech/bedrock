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

package com.cibo.uidocumentation

import com.cibo.bedrock.ReactGridStrict._
import com.cibo.bedrock.navigation.NavigationPage
import com.cibo.uidocumentation.DocumentationRouter._
import com.cibo.uidocumentation.example.MetaComponentExample
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.TagMod
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.document
import org.scalajs.dom.ext.PimpedNodeList
import org.scalajs.dom.html.LI

object DocsHomePage {

  def docsPage(page: NavigationPage[DocumentationPage]): VdomTagOf[LI] =
    <.li(
      <.a(page.name, ^.href := GlobalNavState.getCtl.urlFor(page.link).value)
    )

  def applySyntaxHighlight = Callback {
    import scala.scalajs.js.Dynamic.{global => g}
    val nodeList = document.querySelectorAll("pre code").toArray
    nodeList.foreach(g.window.hljs highlightBlock _)
  }

  def static() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Bedrock"))),
      <.div(
        ^.cls := "main-view",
        column(12)(
          <.h4("Components"),
          <.ul(
            TagMod.Composite(
              DocumentationRouter.pages.map(docsPage).toVector
            )
          )
        ),
        column(12)(
          <.h4("Scala Dependencies"),
          <.h5(
            <.a(
              "Find latest version here",
              ^.href := "https://cibotech.jfrog.io/cibotech/webapp/#/artifacts/browse/tree/General/libs-release-local/com/cibo/bedrock_sjs0.6_2.12"
            )),
          CodeExample("scala",
                      s"""
                 | "com.cibo" %%% "bedrock" % bedrockVersion
                """.stripMargin)
        ),
        column(12)(
          <.h4("Style Dependencies"),
          CodeExample(
            "html",
            s"""
                 | Add include this to your html header
                 | <link rel="stylesheet" href="https://bedrock.cibo.tech/resources/css/main.css" />
                """.stripMargin
          )
        ),
        column(12)(
          <.h4("Example Component"),
          row(
            column(6)(
              MetaComponentExample()
            ),
            column(12)(
              CodeExample(
                "scala",
                """
                  |Pane(
                  |  Some(PaneHeader(Some("Form Example".bold)))
                  |)(
                  |  row(
                  |    Padding(10),
                  |    column(12)(
                  |      spreadForm("I'm a boss",
                  |                 ToggleInput(
                  |                   toggled = s.isABoss,
                  |                   onChange = { aBoss =>
                  |                     $.modState(_.copy(isABoss = aBoss))
                  |                   }
                  |                 ))
                  |    ),
                  |    column(12)(
                  |      spreadForm("Name", TextInput(initialValue = s.name, onChange = { name =>
                  |        $.modState(_.copy(name = name))
                  |      }))
                  |    ),
                  |    column(12)(
                  |      spreadForm("I like cats",
                  |                 CheckBoxInput(
                  |                   checked = s.likesCats,
                  |                   onChange = { likesCats =>
                  |                     $.modState(_.copy(likesCats = likesCats))
                  |                   }
                  |                 ))
                  |    ),
                  |    column(12)(
                  |      formElement(
                  |        Button("click me".medium, buttonSizedText = false).small.margin(Some(Margin(5, 0, 0, 0)))
                  |          .onClick(Callback { println("clicked") })
                  |      )
                  |    )
                  |  )
                  |)
                 """.stripMargin
              )
            )
          )
        )
      )
    )
  }

  val component = ScalaComponent.static("DocsHomePage")(static())
}
