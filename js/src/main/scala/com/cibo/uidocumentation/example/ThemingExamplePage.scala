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

import com.cibo.bedrock.ReactGridStrict.{column, row}
import com.cibo.uidocumentation.CodeExample
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{Callback, ScalaComponent}
import org.scalajs.dom

object ThemingExamplePage {

  val themes = Seq("default", "dark", "ciboneu")

  def currentTheme = {
    val themeHeader = dom.document.head.getElementsByClassName("bedrock-theme")(0)
    val theme = themeHeader.attributes.getNamedItem("href").value.split('/').last.split('.').head
    theme
  }

  def changeCurrentTheme(theme: String) = {
    val themeHeader = dom.document.head.getElementsByClassName("bedrock-theme")(0)
    themeHeader.attributes.getNamedItem("href").value = s"/resources/css/$theme.css"
  }

  def render() = {
    <.div(
      ^.cls := "style-guide",
      <.div(^.cls := "intro", <.div(^.cls := "interior", <.h4("Theming"))),
      <.div(
        ^.cls := "main-view",
        row(
          column(12)(
            <.p(
              "Theming support is currently a work in progress." +
                "Themes are currently defined by modifying " +
                "the parameters in resouces/style-config.scss and rebuilding bedrock. " +
                "This is a WIP and will be much more streamlined in the future.")
          )
        ),
        row(
          column(12)(
            column(12)(
              <.h4("Themes"),
              <.ul(
                themes.map { theme =>
                  <.li(
                    <.a(theme, ^.onClick --> Callback(changeCurrentTheme(theme)))
                  )
                }.toTagMod
              )
            )
          )
        ),
        row(
          column(12)(
            <.h4("Current Theme Example"),
            CodeExample(
              "scss",
              """
                  |$primary-color: #008080;
                  |$contrast-with-primary: #f0f0f0;
                  |
                  |$secondary-color: #485155;
                  |$complement-secondary: lighten($secondary-color, 10%);
                  |$contrast-with-secondary: #e0e0e0;
                  |
                  |$accent-color: #008080;
                  |
                  |$font-color: #393939;
                  |$adjacent-font-color: #7a7a7a;
                  |$inverse-font-color: #f0f0f0;
                  |
                  |$body-background: #f0f0f0;
                  |$card-background: #FEFEFE;
                  |
                  |// Navigation
                  |$dashboard-vertical-menu-height: 40px;
                  |$dashboard-menu-width: 200px;
                  |
                  |$horizontal-background-color: $card-background;
                  |$vertical-background-color: $card-background;
                  |$current-item-color: $primary-color;
                  |
                  |$mobile-width-min: 1024px;
                  |$dashboard-menu-border: 1px solid darken($vertical-background-color, 10%);
                  |$menu-font-color: $font-color;
                """.stripMargin
            )
          )
        )
      )
    )

  }

  val component = ScalaComponent.static("ThemingExample")(render())
}
